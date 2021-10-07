package br.com.nivlabs.gp.service.userservice.business;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.controller.filters.UserFilters;
import br.com.nivlabs.gp.enums.DocumentType;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.Person;
import br.com.nivlabs.gp.models.domain.PersonDocument;
import br.com.nivlabs.gp.models.domain.Role;
import br.com.nivlabs.gp.models.domain.UserApplication;
import br.com.nivlabs.gp.models.dto.AddressDTO;
import br.com.nivlabs.gp.models.dto.DocumentDTO;
import br.com.nivlabs.gp.models.dto.RoleDTO;
import br.com.nivlabs.gp.models.dto.UserDTO;
import br.com.nivlabs.gp.models.dto.UserInfoDTO;
import br.com.nivlabs.gp.repository.PersonRepository;
import br.com.nivlabs.gp.repository.UserRepository;
import br.com.nivlabs.gp.service.BaseBusinessHandler;

/**
 * 
 * Componente específico para consulta de usuários
 *
 * @author viniciosarodrigues
 * @since 27-09-2021
 *
 */
@Component
public class SearchUserBusinessHandler implements BaseBusinessHandler {

    @Autowired
    private Logger logger;

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PersonRepository personRepo;

    /**
     * Consulta página filtrada de usuários
     * 
     * @param filters Filtros de consulta
     * @param pageSettings Configurações de paginação
     * @return Página filtrada de usuários
     */
    public Page<UserDTO> getPage(UserFilters filters, Pageable pageSettings) {
        return userRepo.resumedList(filters, pageSettings);
    }

    /**
     * Busca um usuário por nome de usuário
     * 
     * @param username nome do usuário
     * @return Informações detalhadas do usuário
     */
    @Transactional
    public UserInfoDTO byUserName(String username) {
        UserApplication userEntity = userRepo.findByUserName(username)
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND, "O usuário '" + username + "' não foi encontrado"));

        return convertEntityToDTO(userEntity);
    }

    /**
     * Busca um usuário por nome de identificador único
     * 
     * @param id Identificador único do usuário
     * @return Informações detalhadas do usuário
     */
    @Transactional
    public UserInfoDTO byId(Long id) {
        UserApplication userEntity = userRepo.findById(id)
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND, "Usuário com id '" + id + "' não encontrado"));

        return convertEntityToDTO(userEntity);
    }

    /**
     * Consulta informações do usuário à partir do CPF
     * 
     * @param cpf CPF do usuário
     * @return Informações detalhadas do usuário
     */
    @Transactional
    public UserInfoDTO byCPF(String cpf) {
        try {
            UserApplication user = userRepo.findByCpf(cpf).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                    String.format("Usuário com CPF %s não encontrado", cpf)));

            return convertEntityToDTO(user);
        } catch (HttpException e) {
            if (e.getStatus() == HttpStatus.NOT_FOUND) {
                logger.warn(e.getMessage());
                logger.info("Iniciando busca de informações de pessoa física por CPF :: {}", cpf);
                Person personFromDb = personRepo.findByCpf(cpf)
                        .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND, "Usuário não encontrado no CPF " + cpf));

                UserApplication user = new UserApplication();
                user.setPerson(personFromDb);

                return convertEntityToDTO(user);
            }
            throw e;
        }
    }

    /**
     * Converte entidade do modelo relacional em objeto de transferência
     * 
     * @param userEntity Entidade do modelo relacional
     * @return Objeto de transferência
     */
    @Transactional
    private UserInfoDTO convertEntityToDTO(UserApplication userEntity) {
        logger.info("Iniciando processo de conversão de dados de entidade para objeto de transferência :: Patient -> PatientInfoDTO");
        Person person = userEntity.getPerson();

        UserInfoDTO userInfo = new UserInfoDTO();
        userInfo.setId(userEntity.getId());
        userInfo.setFullName(person.getFullName());
        userInfo.setSocialName(person.getSocialName());
        userInfo.setBornDate(person.getBornDate());
        userInfo.setDocument(new DocumentDTO(null, DocumentType.CPF, person.getCpf(), null, null, null, null));
        userInfo.setDocuments(convertDocuments(person.getDocuments()));
        userInfo.setGender(person.getGender());
        userInfo.setGenderIdentity(person.getGenderIdentity());
        userInfo.setFatherName(person.getFatherName());
        userInfo.setMotherName(person.getMotherName());
        userInfo.setPrincipalNumber(person.getPrincipalNumber());
        userInfo.setSecondaryNumber(person.getSecondaryNumber());
        userInfo.setEmail(person.getEmail());

        if (person.getAddress() != null) {
            AddressDTO address = new AddressDTO();
            address.setAddressNumber(person.getAddress().getAddressNumber());
            address.setCity(person.getAddress().getCity());
            address.setComplement(person.getAddress().getComplement());
            address.setNeighborhood(person.getAddress().getNeighborhood());
            address.setPostalCode(person.getAddress().getPostalCode());
            address.setState(person.getAddress().getState());
            address.setStreet(person.getAddress().getStreet());
            userInfo.setAddress(address);
        }

        userInfo.setProfilePhoto(person.getProfilePhoto());
        userInfo.setCreatedAt(userEntity.getCreatedAt());
        userInfo.setUserName(userEntity.getUserName());
        userInfo.setActive(userEntity.isActive());
        userInfo.setFirstSignin(userEntity.isFirstSignin());

        userInfo.setEthnicGroup(person.getEthnicGroup());
        userInfo.setBloodType(person.getBloodType());
        userInfo.setNationality(person.getNationality());

        if (userEntity.getRoles() != null && !userEntity.getRoles().isEmpty()) {
            userInfo.setRoles(userEntity.getRoles().stream().map(this::convertRole).toList());
        }

        return userInfo;
    }

    /**
     * Converte uma role da entidade relacional para uma role do objeto de transferência
     * 
     * @param role Role de acesso do usuário
     * @return Objeto convertifo para objeto de tranferência
     */
    private RoleDTO convertRole(Role role) {
        return new RoleDTO(role.getId(), role.getName(), role.getDescription());
    }

    /**
     * Converte documentos de domínio para documentos de transferência
     * 
     * @param documents Lista de documentos à serem convertidos
     * @return Lista de documentos convertidos
     */
    @Transactional
    private List<DocumentDTO> convertDocuments(List<PersonDocument> documents) {
        List<DocumentDTO> convertedDocuments = new ArrayList<>();

        documents.forEach(doc -> {
            DocumentDTO convertedDoc = new DocumentDTO();
            convertedDoc.setPersonId(doc.getId().getPersonId());
            convertedDoc.setType(doc.getId().getType());
            convertedDoc.setValue(doc.getId().getValue());
            convertedDocuments.add(convertedDoc);
        });

        return convertedDocuments;
    }
}
