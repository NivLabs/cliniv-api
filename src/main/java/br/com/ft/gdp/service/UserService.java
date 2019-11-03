package br.com.ft.gdp.service;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.ft.gdp.exception.ObjectNotFoundException;
import br.com.ft.gdp.exception.ValidationException;
import br.com.ft.gdp.models.domain.Person;
import br.com.ft.gdp.models.domain.PersonAddress;
import br.com.ft.gdp.models.domain.PersonPhone;
import br.com.ft.gdp.models.domain.UserApplication;
import br.com.ft.gdp.models.dto.UserInfoDTO;
import br.com.ft.gdp.models.enums.DocumentType;
import br.com.ft.gdp.repository.UserRepository;

/**
 * Classe UserService.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 15 de set de 2019
 */
@Service
public class UserService implements GenericService<UserApplication, Long> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PersonService personService;

    public UserApplication findByUsername(String username) {
        return userRepo.findByUsername(username).orElseThrow(() -> new ObjectNotFoundException(
                "Usuário não encontrado! Username: " + username + ", tipo " + UserApplication.class.getName()));
    }

    @Override
    public Page<UserApplication> searchEntityPage(Pageable pageRequest) {
        return null;
    }

    @Override
    public UserApplication findById(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "Usuário não encontrado! Id: " + id + ", tipo " + UserApplication.class.getName()));
    }

    @Override
    public UserApplication update(Long id, UserApplication entity) {
        return null;
    }

    @Override
    public UserApplication persist(UserApplication entity) {
        return userRepo.save(entity);
    }

    /**
     * Atualiza ou cria informações de usuários
     * 
     * @param id
     * @param entity
     * @return
     */
    public UserInfoDTO createOrUpdateUserInfo(Long id, UserInfoDTO entity) {
        UserApplication user = findById(id);
        Person person = null;

        logger.info("Este usuário já possui um cadastro de pessoa física. Iniciando a atualização do cadastro...");
        person = getPersonFromUserInfo(user, entity);
        logger.info("Persistindo informações no banco de dados...");
        person = personService.update(person.getId(), person);

        user.setPerson(person);
        return entity;
    }

    /**
     * 
     * Trata uma pessoa já existente na base à partir do DTO
     * 
     * @param entity
     * @return
     */
    private Person getPersonFromUserInfo(UserApplication user, UserInfoDTO entity) {
        Person personToUpdate = user.getPerson();
        BeanUtils.copyProperties(entity, personToUpdate, "id");
        logger.info("Copiando propriedades do objeto externo para a atualização :: {}", entity.toString());

        if (entity.getDocument().getType() != DocumentType.CPF) {
            logger.error("Tipo de documento inválido :: Informado -> {} Esperado -> CPF", entity.getDocument().getType());
            throw new ValidationException("O tipo do documento deve ser CPF");
        }
        personToUpdate.setCpf(entity.getDocument().getValue());

        if (entity.getAddress() != null) {
            logger.info("Propriedade de endereço encontrada :: Copriando propriedades do objeto externo paa um novo cadastro.");
            PersonAddress addressToUpdate = personToUpdate.getListOfAddress().isEmpty() ? new PersonAddress()
                                                                                        : personToUpdate.getListOfAddress().get(0);
            BeanUtils.copyProperties(entity.getAddress(), addressToUpdate, "id");
            logger.info("Adicionando endereço atualizado ao cadastro de Pessoa Física...");
            personToUpdate.getListOfAddress().add(0, addressToUpdate);
        } else {
            personToUpdate.setListOfAddress(Arrays.asList());
        }

        if (entity.getPhoneNumber() != null) {
            logger.info("Telefone principal informado :: Adicionando à lista de telfones da Pessoa Física | {}", entity.getPhoneNumber());
            PersonPhone principalPhone = new PersonPhone();
            principalPhone.setPersonId(personToUpdate.getId());
            principalPhone.setPhoneNumber(entity.getPhoneNumber());
            personToUpdate.getPhones().add(principalPhone);

        }

        if (entity.getSecondaryNumber() != null) {
            logger.info("Telefone secundário informado :: Adicionando à lista de telfones da Pessoa Física | {}",
                        entity.getSecondaryNumber());
            PersonPhone secondaryPhone = new PersonPhone();
            secondaryPhone.setPersonId(personToUpdate.getId());
            secondaryPhone.setPhoneNumber(entity.getSecondaryNumber());
            personToUpdate.getPhones().add(secondaryPhone);
        }

        return personToUpdate;
    }
}
