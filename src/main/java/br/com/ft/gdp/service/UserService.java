package br.com.ft.gdp.service;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.ft.gdp.controller.filters.UserFilters;
import br.com.ft.gdp.exception.ObjectNotFoundException;
import br.com.ft.gdp.exception.ValidationException;
import br.com.ft.gdp.models.domain.Person;
import br.com.ft.gdp.models.domain.PersonAddress;
import br.com.ft.gdp.models.domain.Role;
import br.com.ft.gdp.models.domain.UserApplication;
import br.com.ft.gdp.models.dto.AddressDTO;
import br.com.ft.gdp.models.dto.DataTransferObjectBase;
import br.com.ft.gdp.models.dto.DocumentDTO;
import br.com.ft.gdp.models.dto.RoleDTO;
import br.com.ft.gdp.models.dto.UserDTO;
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
        UserApplication entityFromDb = userRepo.findByUserName(username).orElseThrow(() -> new ObjectNotFoundException(
                "Usuário não encontrado! Username: " + username + ", tipo " + UserApplication.class.getName()));

        UserInfoDTO responseDTO = new UserInfoDTO();

        Person person = entityFromDb.getPerson();
        BeanUtils.copyProperties(person, responseDTO, "id");
        BeanUtils.copyProperties(entityFromDb, responseDTO);
        responseDTO.setDocument(new DocumentDTO(DocumentType.CPF, person.getCpf()));

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

    public UserInfoDTO findById(Long id) {
        UserApplication userFromDb = userRepo.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "Usuário não encontrado! Id: " + id + ", tipo " + UserApplication.class.getName()));

        UserInfoDTO response = new UserInfoDTO();

        BeanUtils.copyProperties(userFromDb.getPerson(), response);

        if (userFromDb.getPerson().getAddress() != null) {
            AddressDTO address = new AddressDTO();
            BeanUtils.copyProperties(userFromDb.getPerson().getAddress(), address);
            response.setAddress(address);
        }

        response.setDocument(new DocumentDTO(DocumentType.CPF, userFromDb.getPerson().getCpf()));

        if (userFromDb.getRoles() != null && !userFromDb.getRoles().isEmpty()) {
            response.setRoles(userFromDb.getRoles().stream().map(this::convertRole).collect(Collectors.toList()));
        }

        BeanUtils.copyProperties(userFromDb, response);

        return response;
    }

    public DataTransferObjectBase persist(UserApplication entity) {
        return null;
    }

    /**
     * Atualiza ou cria informações de usuários
     * 
     * @param userId
     * @param entity
     * @return
     */
    public UserInfoDTO update(Long userId, UserInfoDTO entity) {
        UserApplication entityFromDb = userRepo.findByUserName(entity.getUserName()).orElseThrow(() -> new ObjectNotFoundException(
                "Usuário não encontrado! Username: " + entity.getUserName() + ", tipo " + UserApplication.class.getName()));

        entityFromDb.setPerson(getPersonFromUserInfo(entityFromDb.getPerson(), entity));
        BeanUtils.copyProperties(entity, entityFromDb);

        entityFromDb.setRoles(Arrays.asList());
        if (entity.getRoles() != null && !entity.getRoles().isEmpty()) {
            entityFromDb.setRoles(entity.getRoles().stream().map(this::convertRole).collect(Collectors.toList()));
        }

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
        BeanUtils.copyProperties(entity, personToUpdate, "id");
        if (entity.getAddress() != null) {
            if (personToUpdate.getAddress() == null) {
                PersonAddress newAddress = new PersonAddress();
                newAddress.setPerson(personToUpdate);
                personToUpdate.setAddress(newAddress);
            }
            BeanUtils.copyProperties(entity.getAddress(), personToUpdate.getAddress());
        }

        if (entity.getDocument().getType() != DocumentType.CPF) {
            throw new ValidationException("O tipo do documento deve ser CPF");
        }
        personToUpdate.setCpf(entity.getDocument().getValue());

        return personToUpdate;
    }

    public UserInfoDTO findPersonByCpf(String cpf) {
        Person personFromDb = personService.findByCpf(cpf);

        UserInfoDTO userInfo = new UserInfoDTO();
        BeanUtils.copyProperties(personFromDb, userInfo, "id");
        userInfo.setDocument(new DocumentDTO(DocumentType.CPF, personFromDb.getCpf()));

        if (personFromDb.getAddress() != null) {
            AddressDTO address = new AddressDTO();
            BeanUtils.copyProperties(personFromDb.getAddress(), address);
            userInfo.setAddress(address);
        }

        return userInfo;
    }

    public UserInfoDTO findByCpf(String cpf) {
        try {
            UserApplication user = userRepo.findByCpf(cpf)
                    .orElseThrow(() -> new ObjectNotFoundException(String.format("Usuário com cpf: [%s] não encontrado", cpf)));
            Person personFromDb = user.getPerson();

            UserInfoDTO userInfo = new UserInfoDTO();
            BeanUtils.copyProperties(personFromDb, userInfo, "id");
            BeanUtils.copyProperties(user, userInfo);
            userInfo.setDocument(new DocumentDTO(DocumentType.CPF, personFromDb.getCpf()));

            if (personFromDb.getAddress() != null) {
                AddressDTO address = new AddressDTO();
                BeanUtils.copyProperties(personFromDb.getAddress(), address);
                userInfo.setAddress(address);
            }

            userInfo.setId(userInfo.getId());
            return userInfo;
        } catch (ObjectNotFoundException e) {
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
                throw new ValidationException(String.format("Usuário com o CPF %s já cadastrado.", entity.getDocument().getValue()));
            }
            user = findByUserName(entity.getUserName());

            if (user != null && user.getId() != null) {
                logger.warn("Nome de usuário {} já cadastrado.", entity.getUserName());
                throw new ValidationException(String.format("Nome de usuário %s já cadastrado.", entity.getUserName()));
            }
        } catch (ObjectNotFoundException e) {
            logger.info("Nenhum cadastro de usuário encontrado :: CPF da busca -> {}",
                        entity.getDocument().getValue());
            logger.info("Continuando cadastro de usuário...");
        }
    }

    public UserInfoDTO create(UserInfoDTO entity) {
        entity.setId(null);

        Person personFromDb;
        if (entity.getDocument() == null || (entity.getDocument() != null && entity.getDocument().getType() != DocumentType.CPF)) {
            logger.error("Tipo do documento inválido, informe um documento válido");
            throw new ValidationException("Tipo do documento inválido, informe um documento válido.");
        }
        userCheckIfExists(entity);
        try {
            logger.info("Verificando se já existe um cadastro anexado ao documento informado...");
            personFromDb = personService.findByCpf(entity.getDocument().getValue());
        } catch (ObjectNotFoundException e) {
            logger.info("Nenhum cadastro encontrado :: Criando um novo cadastro de Pessoa no documento :: TIPO: {} | VALOR: {}",
                        entity.getDocument().getType(), entity.getDocument().getValue());
            personFromDb = new Person();
        }

        logger.info("Copiando as propriedades da requisição para o obijeto de negócio...");
        BeanUtils.copyProperties(entity, personFromDb, "id");
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

        UserApplication user = new UserApplication();
        BeanUtils.copyProperties(entity, user);
        user.setPerson(personFromDb);
        user.setPassword(bc.encode(entity.getDocument().getValue()));
        user.setActive(true);
        if (entity.getRoles() != null && !entity.getRoles().isEmpty()) {
            user.setRoles(entity.getRoles().stream().map(this::convertRole).collect(Collectors.toList()));
        }
        userRepo.save(user);

        entity.setId(user.getId());
        return entity;
    }
}
