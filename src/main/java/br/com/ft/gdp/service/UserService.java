package br.com.ft.gdp.service;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.ft.gdp.controller.utils.FilteredPageable;
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

    public Page<UserDTO> searchEntityPage(FilteredPageable pageRequest) {

        return null;
    }

    public UserInfoDTO findById(Long id) {
        userRepo.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "Usuário não encontrado! Id: " + id + ", tipo " + UserApplication.class.getName()));
        return null;
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
            entityFromDb.setRoles(entity.getRoles().stream().map(this::convertRoleDto).collect(Collectors.toList()));
        }

        userRepo.saveAndFlush(entityFromDb);

        return entity;
    }

    /**
     * Converte um papel vinda da requisição em um papel de domínio
     * 
     * @param roleDTO
     * @return
     */
    private Role convertRoleDto(RoleDTO roleDTO) {
        return new Role(roleDTO.getId(), roleDTO.getDescription());
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
}
