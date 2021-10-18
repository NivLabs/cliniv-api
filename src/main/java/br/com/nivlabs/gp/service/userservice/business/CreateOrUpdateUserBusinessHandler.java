package br.com.nivlabs.gp.service.userservice.business;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.models.domain.Role;
import br.com.nivlabs.gp.models.domain.UserApplication;
import br.com.nivlabs.gp.models.dto.PersonInfoDTO;
import br.com.nivlabs.gp.models.dto.RoleDTO;
import br.com.nivlabs.gp.models.dto.UserInfoDTO;
import br.com.nivlabs.gp.repository.PersonDocumentRepository;
import br.com.nivlabs.gp.repository.UserRepository;
import br.com.nivlabs.gp.service.BaseBusinessHandler;
import br.com.nivlabs.gp.service.person.PersonService;

/**
 * Componente específico para criação ou atualização de dados cadastrais do usuário
 * 
 *
 * @author viniciosarodrigues
 * @since 28-09-2021
 *
 */
@Component
public abstract class CreateOrUpdateUserBusinessHandler implements BaseBusinessHandler {

    @Autowired
    protected Logger logger;

    @Autowired
    protected UserRepository userRepo;
    @Autowired
    protected PersonDocumentRepository documentRepo;
    @Autowired
    PersonService personService;

    /**
     * Realiza o parse de informações do objeto de transferência para o objeto de entidade relacional de pessoa física
     * 
     * @param userInfo Objeto de transferência com informações da pessoa física
     * @param personEntity Objeto de entidade relacional de pessoa física
     */
    protected void parsePropertiesToPerson(UserInfoDTO userInfo, PersonInfoDTO personInfo) {
        personInfo.setFullName(userInfo.getFullName());
        personInfo.setSocialName(userInfo.getSocialName());
        personInfo.setDocument(userInfo.getDocument());
        personInfo.setGender(userInfo.getGender());
        personInfo.setGenderIdentity(userInfo.getGenderIdentity());
        personInfo.setFatherName(userInfo.getFatherName());
        personInfo.setMotherName(userInfo.getMotherName());
        personInfo.setBornDate(userInfo.getBornDate());
        personInfo.setPrincipalNumber(userInfo.getPrincipalNumber());
        personInfo.setSecondaryNumber(userInfo.getSecondaryNumber());
        personInfo.setProfilePhoto(userInfo.getProfilePhoto());
        personInfo.setEmail(userInfo.getEmail());
        personInfo.setEthnicGroup(userInfo.getEthnicGroup());
        personInfo.setBloodType(userInfo.getBloodType());
        personInfo.setNationality(userInfo.getNationality());
        personInfo.setAddress(userInfo.getAddress());
        personInfo.setDocuments(userInfo.getDocuments());
    }

    /**
     * Converte os papéis de acesso do usuário
     * 
     * @param userInfo Informações do usuário
     * @param userEntity Entidade relacional de usuário
     */
    protected void convertRoles(UserInfoDTO userInfo, UserApplication userEntity) {
        if (userInfo.getRoles() != null && !userInfo.getRoles().isEmpty()) {
            userEntity.setRoles(userInfo.getRoles().stream().map(this::convertRole).collect(Collectors.toList()));
        }
    }

    /**
     * Converte um papel vindo da requisição em um papel de domínio
     * 
     * @param roleDTO Papel de acesso do usuário em Entidade Relacional
     * @return Papel de acesso do usuário convertido em DTO
     */
    private Role convertRole(RoleDTO roleDTO) {
        return new Role(roleDTO.getId(), roleDTO.getName(), roleDTO.getDescription());
    }

}
