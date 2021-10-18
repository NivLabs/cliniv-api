package br.com.nivlabs.gp.service.userservice.business;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.UserApplication;
import br.com.nivlabs.gp.models.dto.PersonInfoDTO;
import br.com.nivlabs.gp.models.dto.UserInfoDTO;

/**
 * Componente específico para atualização dos dados de usuário
 *
 * @author viniciosarodrigues
 * @since 28-09-2021
 *
 */
@Component
public class UpdateUserBusinessHandler extends CreateOrUpdateUserBusinessHandler {

    /**
     * Atualiza informações do usuário
     * 
     * @param userInfo Informações atualizadas do usuário
     * @return Usuário com dados atualizados
     */
    public UserInfoDTO update(UserInfoDTO userInfo) {
        logger.info("Iniciando processo de atualizado dos dados do usuário");
        UserApplication userEntity = userRepo.findById(userInfo.getId())
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                        "Usuário não encontrado para o identificador " + userInfo.getId()));

        PersonInfoDTO personInfoToUpdate = new PersonInfoDTO();
        personInfoToUpdate.setId(userEntity.getPerson().getId());
        parsePropertiesToPerson(userInfo, personInfoToUpdate);

        personService.update(personInfoToUpdate);

        persistUser(userInfo, userEntity);

        return userInfo;
    }

    /**
     * Insere um usuário na base de dados
     * 
     * @param userInfo Informações do usuário
     * @param userEntity Entidade usuário
     * @return Usuário da aplicação
     */
    private void persistUser(UserInfoDTO userInfo, UserApplication userEntity) {
        if (userInfo.getId() == null) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Informe o identificador do usuário para que seja possível atualizar as informações do mesmo.");
        }
        userEntity.setUserName(userInfo.getUserName());
        userEntity.setActive(userInfo.isActive());
        userEntity.setFirstSignin(userInfo.isFirstSignin());
        convertRoles(userInfo, userEntity);
        userRepo.saveAndFlush(userEntity);
    }

}
