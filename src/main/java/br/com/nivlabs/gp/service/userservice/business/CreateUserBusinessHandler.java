package br.com.nivlabs.gp.service.userservice.business;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.enums.DocumentType;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.Person;
import br.com.nivlabs.gp.models.domain.UserApplication;
import br.com.nivlabs.gp.models.dto.PersonInfoDTO;
import br.com.nivlabs.gp.models.dto.UserInfoDTO;

/**
 * 
 * Componente específico para criação de usuário na aplicação
 *
 * @author viniciosarodrigues
 * @since 28-09-2021
 *
 */
@Component
public class CreateUserBusinessHandler extends CreateOrUpdateUserBusinessHandler {

    @Autowired
    private BCryptPasswordEncoder bc;
    @Autowired
    protected SearchUserBusinessHandler searchUserBusinessHandler;

    /**
     * Cria um usuário um usuário na aplicação
     * 
     * @param userInfo Informações do novo usuário
     * @return Informações do usuário criado
     */
    public UserInfoDTO create(UserInfoDTO userInfo) {
        userInfo.setId(null);

        PersonInfoDTO personInfo = getValidPerson(userInfo);

        parsePropertiesToPerson(userInfo, personInfo);

        if (personInfo.getId() != null)
            personService.update(personInfo);
        else
            personService.create(personInfo);

        UserApplication createdUser = persistUser(userInfo, personInfo);
        userInfo.setId(createdUser.getId());

        return userInfo;
    }

    /**
     * Busca uma pessoa válida baseada nas informações do usuário
     * 
     * @param userInfo Informações do usuário à ser criado
     * @return Informações de pessoa física cadastrada (se houver)
     */
    private PersonInfoDTO getValidPerson(UserInfoDTO userInfo) {
        PersonInfoDTO personInfo;
        if (userInfo.getDocument() == null
                || (userInfo.getDocument() != null && userInfo.getDocument().getType() != DocumentType.CPF)) {
            logger.error("Tipo do documento inválido, informe um documento válido");
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "Tipo do documento inválido, informe um documento válido.");
        }
        userCheckIfExists(userInfo);
        try {
            logger.info("Verificando se já existe um cadastro anexado ao documento informado...");
            personInfo = personService.findByCpf(userInfo.getDocument().getValue());
        } catch (HttpException e) {
            logger.info(
                        "Nenhum cadastro encontrado :: Criando um novo cadastro de Pessoa no documento :: TIPO: {} | VALOR: {}",
                        userInfo.getDocument().getType(), userInfo.getDocument().getValue());
            personInfo = new PersonInfoDTO();
        }
        return personInfo;
    }

    /**
     * Verifica se já existe cadastro prévio do usuário
     * 
     * @param userInfo Informações do usuário
     */
    private void userCheckIfExists(UserInfoDTO userInfo) {
        try {
            logger.info("Verificando se já há cadastro de usuário na base de dados :: CPF da busca -> {}",
                        userInfo.getDocument().getValue());
            UserInfoDTO user = searchUserBusinessHandler.byCPF(userInfo.getDocument().getValue());
            if (user != null && user.getId() != null) {
                logger.warn("Usuário com o CPF {} já cadastrado.", userInfo.getDocument().getValue());
                throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                        String.format("Usuário com o CPF %s já cadastrado.", userInfo.getDocument().getValue()));
            }
            user = searchUserBusinessHandler.byUserName(userInfo.getUserName());

            if (user != null && user.getId() != null) {
                logger.warn("Nome de usuário {} já cadastrado.", userInfo.getUserName());
                throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                        String.format("Nome de usuário %s já cadastrado.", userInfo.getUserName()));
            }
        } catch (HttpException e) {
            logger.info("Nenhum cadastro de usuário encontrado :: CPF da busca -> {}", userInfo.getDocument().getValue());
            logger.info("Continuando cadastro de usuário...");
        }
    }

    /**
     * Insere um usuário na base de dados
     * 
     * @param entity Informações do usuário
     * @param personFromDb informações da pessoa física
     * @return Usuário criado na aplicação
     */
    private UserApplication persistUser(UserInfoDTO entity, PersonInfoDTO personFromDb) {
        UserApplication user = new UserApplication();
        user.setId(entity.getId());
        user.setUserName(entity.getUserName());
        user.setPerson(new Person(personFromDb.getId()));
        user.setPassword(bc.encode(entity.getDocument().getValue()));
        user.setCreatedAt(LocalDateTime.now());
        user.setActive(true);
        user.setFirstSignin(true);
        convertRoles(entity, user);
        userRepo.save(user);
        return user;
    }
}
