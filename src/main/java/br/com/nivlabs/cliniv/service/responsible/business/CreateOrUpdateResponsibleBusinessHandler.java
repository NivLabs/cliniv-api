package br.com.nivlabs.cliniv.service.responsible.business;

import java.util.ArrayList;

import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.Responsible;
import br.com.nivlabs.cliniv.models.dto.PersonInfoDTO;
import br.com.nivlabs.cliniv.models.dto.ResponsibleInfoDTO;
import br.com.nivlabs.cliniv.models.dto.SpecialityDTO;
import br.com.nivlabs.cliniv.repository.ResponsibleRepository;
import br.com.nivlabs.cliniv.repository.SpecialityRepository;
import br.com.nivlabs.cliniv.service.BaseBusinessHandler;
import br.com.nivlabs.cliniv.service.person.PersonService;

/**
 * 
 * Componente específico para base de criação ou atualização de Profissional ou Responsável
 *
 * @author viniciosarodrigues
 * @since 27-09-2021
 *
 */
@Component
public abstract class CreateOrUpdateResponsibleBusinessHandler implements BaseBusinessHandler {

    protected static final String RESPONSIBLE_NOT_FOUND = "Responsável com o identificador %s não encontrado";

    @Autowired
    protected Logger logger;

    @Autowired
    protected SpecialityRepository specialityRepository;
    @Autowired
    protected ResponsibleRepository responsibleRepo;
    @Autowired
    protected SearchResponsibleBusinessHandler searchResponsibleBusiness;
    @Autowired
    protected PersonService personService;

    /**
     * Trata as especializações para atualização
     * 
     * @param responsible
     * @param responsibleFrom
     */
    protected void handleSpecializations(ResponsibleInfoDTO responsible, Responsible responsibleFrom) {
        logger.info("Verificando especializações...");
        if (responsible.getSpecializations() != null) {
            logger.info("Alterações encontradas, atualizando especializações do profissional :: {}",
                        responsible.getFullName());
            responsibleFrom.setSpecializations(new ArrayList<>());
            responsible.getSpecializations().stream().map(SpecialityDTO::getId).forEach(specId -> responsibleFrom.getSpecializations()
                    .add(specialityRepository.findById(specId).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                            String.format("Especialidade com identificador %s não encontrada", specId)))));
        }
    }

    /**
     * Realiza um parse do objeto de transferência do Paciente para Entidade Paciente
     * 
     * @param request Objeto de transferência de paciente
     * @param entity Entidade relacional de paciente
     */
    protected void parsePropertiesToEntity(ResponsibleInfoDTO request, Responsible entity) {
        if (request.getProfessionalIdentity() != null) {
            entity.setInitialsIdentity(request.getProfessionalIdentity().getRegisterType());
            entity.setProfessionalIdentity(request.getProfessionalIdentity().getRegisterValue());
        }
    }

    /**
     * Realiza o parse de informações do objeto de transferência para o objeto de entidade relacional de pessoa física
     * 
     * @param responsibleInfo Objeto de transferência com informações do paciente
     * @param personInfo Objeto de transferência com informações de pessoa física
     */
    @Transactional
    protected void parsePropertiesToPersonInfo(ResponsibleInfoDTO responsibleInfo, PersonInfoDTO personInfo) {
        personInfo.setFullName(responsibleInfo.getFullName());
        personInfo.setSocialName(responsibleInfo.getSocialName());
        personInfo.setDocument(responsibleInfo.getDocument());
        personInfo.setGender(responsibleInfo.getGender());
        personInfo.setGenderIdentity(responsibleInfo.getGenderIdentity());
        personInfo.setFatherName(responsibleInfo.getFatherName());
        personInfo.setMotherName(responsibleInfo.getMotherName());
        personInfo.setBornDate(responsibleInfo.getBornDate());
        personInfo.setPrincipalNumber(responsibleInfo.getPrincipalNumber());
        personInfo.setSecondaryNumber(responsibleInfo.getSecondaryNumber());
        personInfo.setProfilePhoto(responsibleInfo.getProfilePhoto());
        personInfo.setEmail(responsibleInfo.getEmail());
        personInfo.setEthnicGroup(responsibleInfo.getEthnicGroup());
        personInfo.setBloodType(responsibleInfo.getBloodType());
        personInfo.setNationality(responsibleInfo.getNationality());
        personInfo.setAddress(responsibleInfo.getAddress());
        personInfo.setDocuments(responsibleInfo.getDocuments());
    }

}
