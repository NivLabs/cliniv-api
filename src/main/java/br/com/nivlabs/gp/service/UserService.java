package br.com.nivlabs.gp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.controller.filters.UserFilters;
import br.com.nivlabs.gp.enums.DocumentType;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.BaseObjectWithCreatedAt_;
import br.com.nivlabs.gp.models.domain.Person;
import br.com.nivlabs.gp.models.domain.PersonAddress;
import br.com.nivlabs.gp.models.domain.Person_;
import br.com.nivlabs.gp.models.domain.Role;
import br.com.nivlabs.gp.models.domain.UserApplication;
import br.com.nivlabs.gp.models.domain.UserApplication_;
import br.com.nivlabs.gp.models.dto.AddressDTO;
import br.com.nivlabs.gp.models.dto.DocumentDTO;
import br.com.nivlabs.gp.models.dto.RoleDTO;
import br.com.nivlabs.gp.models.dto.UserDTO;
import br.com.nivlabs.gp.models.dto.UserInfoDTO;
import br.com.nivlabs.gp.repository.UserRepository;

/**
 * Classe UserService.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 15 de set de 2019
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PersonService personService;
    @Autowired
    private BCryptPasswordEncoder bc;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Busca um usuário por nome de usuário
     * 
     * @param username
     * @return
     */
    public UserInfoDTO findByUserName(String username) {
        UserApplication entityFromDb = userRepo.findByUserName(username)
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                        "Usuário '" + username + "' não existe"));

        UserInfoDTO responseDTO = new UserInfoDTO();

        Person person = entityFromDb.getPerson();
        BeanUtils.copyProperties(person, responseDTO, Person_.ID);
        BeanUtils.copyProperties(entityFromDb, responseDTO);
        responseDTO.setDocument(new DocumentDTO(null, DocumentType.CPF, person.getCpf(), null, null, null, null));

        if (person.getAddress() != null) {
            AddressDTO address = new AddressDTO();
            BeanUtils.copyProperties(person.getAddress(), address);
            responseDTO.setAddress(address);
        }

        return responseDTO;
    }

    public Page<UserDTO> searchEntityPage(UserFilters filters, Pageable pageSettings) {
        return userRepo.resumedList(filters, pageSettings);
    }

    public UserInfoDTO findUserDtoById(Long id) {
        UserApplication userFromDb = userRepo.findById(id).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                "Usuário con o identificado " + id + " não existe"));

        UserInfoDTO response = new UserInfoDTO();

        BeanUtils.copyProperties(userFromDb.getPerson(), response);

        if (userFromDb.getPerson().getAddress() != null) {
            AddressDTO address = new AddressDTO();
            BeanUtils.copyProperties(userFromDb.getPerson().getAddress(), address);
            response.setAddress(address);
        }

        response.setDocument(new DocumentDTO(null, DocumentType.CPF, userFromDb.getPerson().getCpf(), null, null, null, null));

        if (userFromDb.getRoles() != null && !userFromDb.getRoles().isEmpty()) {
            response.setRoles(userFromDb.getRoles().stream().map(this::convertRole).collect(Collectors.toList()));
        }

        BeanUtils.copyProperties(userFromDb, response);

        return response;
    }

    /**
     * Atualiza ou cria informações de usuários
     * 
     * @param userId
     * @param entity
     * @return
     */
    public UserInfoDTO updateProfile(UserInfoDTO entity) {
        UserApplication entityFromDb = userRepo.findByUserName(entity.getUserName())
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                        entity.getUserName() + " não encontrado, não é possível atualizar o perfil"));

        entityFromDb.setPerson(getPersonFromUserInfo(entityFromDb.getPerson(), entity));
        BeanUtils.copyProperties(entity, entityFromDb, UserApplication_.ROLES);

        userRepo.saveAndFlush(entityFromDb);

        return entity;
    }

    /**
     * Converte um papel vindo da requisição em um papel de domínio
     * 
     * @param roleDTO
     * @return
     */
    private Role convertRole(RoleDTO roleDTO) {
        return new Role(roleDTO.getId(), roleDTO.getName(), roleDTO.getDescription());
    }

    /**
     * Convert um papel vindo do banco em um papel de resposta
     * 
     * @param role
     * @return
     */
    private RoleDTO convertRole(Role role) {
        return new RoleDTO(role.getId(), role.getName(), role.getDescription());
    }

    /**
     * 
     * Trata uma pessoa já existente na base à partir do DTO
     * 
     * @param entity
     * @return
     */
    private Person getPersonFromUserInfo(Person personToUpdate, UserInfoDTO entity) {
        BeanUtils.copyProperties(entity, personToUpdate, Person_.ID);
        if (entity.getAddress() != null) {
            if (personToUpdate.getAddress() == null) {
                PersonAddress newAddress = new PersonAddress();
                newAddress.setPerson(personToUpdate);
                personToUpdate.setAddress(newAddress);
            }
            BeanUtils.copyProperties(entity.getAddress(), personToUpdate.getAddress());
        }

        if (entity.getDocument().getType() != DocumentType.CPF) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "O tipo do documento deve ser CPF");
        }
        personToUpdate.setCpf(entity.getDocument().getValue());

        return personToUpdate;
    }

    public UserInfoDTO findPersonByCpf(String cpf) {
        Person personFromDb = personService.findByCpf(cpf);

        UserInfoDTO userInfo = new UserInfoDTO();
        BeanUtils.copyProperties(personFromDb, userInfo, Person_.ID);
        userInfo.setDocument(new DocumentDTO(null, DocumentType.CPF, personFromDb.getCpf(), null, null, null, null));

        if (personFromDb.getAddress() != null) {
            AddressDTO address = new AddressDTO();
            BeanUtils.copyProperties(personFromDb.getAddress(), address);
            userInfo.setAddress(address);
        }

        return userInfo;
    }

    public UserInfoDTO findByCpf(String cpf) {
        try {
            UserApplication user = userRepo.findByCpf(cpf).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                    String.format("Usuário com cpf: [%s] não encontrado", cpf)));
            Person personFromDb = user.getPerson();

            UserInfoDTO userInfo = new UserInfoDTO();
            BeanUtils.copyProperties(personFromDb, userInfo, Person_.ID);
            BeanUtils.copyProperties(user, userInfo);
            userInfo.setDocument(new DocumentDTO(null, DocumentType.CPF, personFromDb.getCpf(), null, null, null, null));

            if (personFromDb.getAddress() != null) {
                AddressDTO address = new AddressDTO();
                BeanUtils.copyProperties(personFromDb.getAddress(), address);
                userInfo.setAddress(address);
            }

            userInfo.setId(userInfo.getId());
            return userInfo;
        } catch (HttpException e) {
            return findPersonByCpf(cpf);
        }
    }

    private void userCheckIfExists(UserInfoDTO entity) {
        try {
            logger.info("Verificando se já há cadastro de usuário na base de dados :: CPF da busca -> {}",
                        entity.getDocument().getValue());
            UserInfoDTO user = findByCpf(entity.getDocument().getValue());
            if (user != null && user.getId() != null) {
                logger.warn("Usuário com o CPF {} já cadastrado.", entity.getDocument().getValue());
                throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                        String.format("Usuário com o CPF %s já cadastrado.", entity.getDocument().getValue()));
            }
            user = findByUserName(entity.getUserName());

            if (user != null && user.getId() != null) {
                logger.warn("Nome de usuário {} já cadastrado.", entity.getUserName());
                throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                        String.format("Nome de usuário %s já cadastrado.", entity.getUserName()));
            }
        } catch (HttpException e) {
            logger.info("Nenhum cadastro de usuário encontrado :: CPF da busca -> {}", entity.getDocument().getValue());
            logger.info("Continuando cadastro de usuário...");
        }
    }

    /**
     * Cria um usuário um usuário na aplicação
     * 
     * @param entity
     * @return
     */
    public UserInfoDTO create(UserInfoDTO entity) {
        entity.setId(null);

        Person personFromDb = getValidPerson(entity);

        logger.info("Copiando as propriedades da requisição para o obijeto de negócio...");
        BeanUtils.copyProperties(entity, personFromDb, Person_.ID);
        personFromDb.setCpf(entity.getDocument().getValue());

        logger.info("Verificando e-mail");
        if (entity.getAddress() != null) {
            AddressDTO address = entity.getAddress();
            PersonAddress personAddress = new PersonAddress();
            if (personFromDb.getAddress() != null)
                personAddress = personFromDb.getAddress();
            BeanUtils.copyProperties(address, personAddress, "id");
            personAddress.setPerson(personFromDb);
            personFromDb.setAddress(personAddress);
        }

        if (personFromDb.getId() != null)
            personService.update(personFromDb.getId(), personFromDb);
        else
            personService.persist(personFromDb);

        UserApplication createdUser = persistUser(entity, personFromDb);
        entity.setId(createdUser.getId());

        return entity;
    }

    /**
     * Insere um usuário na base de dados
     * 
     * @param entity
     * @param personFromDb
     * @return
     */
    private UserApplication persistUser(UserInfoDTO entity, Person personFromDb) {
        UserApplication user = new UserApplication();
        BeanUtils.copyProperties(entity, user);
        user.setPerson(personFromDb);
        user.setPassword(bc.encode(entity.getDocument().getValue()));
        user.setCreatedAt(LocalDateTime.now());
        user.setActive(true);
        handleRoles(entity, user);
        userRepo.save(user);
        return user;
    }

    /**
     * Busca uma pessoa válida baseada nas informações do usuário
     * 
     * @param entity
     * @return
     */
    private Person getValidPerson(UserInfoDTO entity) {
        Person personFromDb;
        if (entity.getDocument() == null
                || (entity.getDocument() != null && entity.getDocument().getType() != DocumentType.CPF)) {
            logger.error("Tipo do documento inválido, informe um documento válido");
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "Tipo do documento inválido, informe um documento válido.");
        }
        userCheckIfExists(entity);
        try {
            logger.info("Verificando se já existe um cadastro anexado ao documento informado...");
            personFromDb = personService.findByCpf(entity.getDocument().getValue());
        } catch (HttpException e) {
            logger.info(
                        "Nenhum cadastro encontrado :: Criando um novo cadastro de Pessoa no documento :: TIPO: {} | VALOR: {}",
                        entity.getDocument().getType(), entity.getDocument().getValue());
            personFromDb = new Person();
        }
        return personFromDb;
    }

    /**
     * Atualiza as informações de um usupario
     * 
     * @param id
     * @param entity
     * @return
     */
    public UserInfoDTO userUpdate(Long id, UserInfoDTO entity) {
        UserApplication entityFromDb = userRepo.findById(id)
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND, "Usuário não encontrado! Identificador informado: " + id));

        entityFromDb.setPerson(getPersonFromUserInfo(entityFromDb.getPerson(), entity));
        BeanUtils.copyProperties(entity, entityFromDb, BaseObjectWithCreatedAt_.CREATED_AT);

        entityFromDb.setRoles(new ArrayList<>());
        handleRoles(entity, entityFromDb);

        userRepo.saveAndFlush(entityFromDb);

        return entity;
    }

    /**
     * Trata os papéis
     * 
     * @param entity
     * @param entityFromDb
     */
    private void handleRoles(UserInfoDTO entity, UserApplication entityFromDb) {
        if (entity.getRoles() != null && !entity.getRoles().isEmpty()) {
            entityFromDb.setRoles(entity.getRoles().stream().map(this::convertRole).collect(Collectors.toList()));
        }
    }

}
