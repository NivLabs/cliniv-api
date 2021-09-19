package br.com.nivlabs.gp.service.patient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.controller.filters.PatientFilters;
import br.com.nivlabs.gp.models.domain.PatientAllergy;
import br.com.nivlabs.gp.models.dto.PatientAllergiesDTO;
import br.com.nivlabs.gp.models.dto.PatientDTO;
import br.com.nivlabs.gp.models.dto.PatientInfoDTO;
import br.com.nivlabs.gp.repository.PatientAllergyRepository;
import br.com.nivlabs.gp.service.BaseService;
import br.com.nivlabs.gp.service.patient.business.CreatePatientBusinessHandler;
import br.com.nivlabs.gp.service.patient.business.SearchPatientBusinessHandler;
import br.com.nivlabs.gp.service.patient.business.UpdatePatientBusinessHandler;

/**
 * 
 * Classe PatientService.java
 *
 * @author <a href="carolexc@gmail.com">Caroline Aguiar</a>
 *
 * @since 15 de set de 2019
 */
@Service
public class PatientService implements BaseService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PatientAllergyRepository patientAllergyDao;
    
    @Autowired
    SearchPatientBusinessHandler patientSearchBusinessHandler;
    @Autowired
    CreatePatientBusinessHandler createPatientBusinessHandler;
    @Autowired
    UpdatePatientBusinessHandler updatePatientBusinessHandler;

    /**
     * Busca uma página de informações resumidas de pacientes
     * 
     * @param filters Filtros de busca
     * @param pageRequest Configurações da paginação
     * @return Página com informações resumidas de pacientes
     */
    public Page<PatientDTO> getPage(PatientFilters filters, Pageable pageRequest) {
        return patientSearchBusinessHandler.getPage(filters, pageRequest);
    }

    /**
     * Busca informações detalhadas do paciente
     * 
     * @param id Identificador único do paciente
     * @return Informações detalhadas do paciente
     * 
     */
    public PatientInfoDTO findByPatientId(Long id) {
        return patientSearchBusinessHandler.getById(id);
    }

    /**
     * Busca informações do paciente
     * 
     * OBS: Se o paciente não for encontrado, uma busca por informações de pessoa física é realizada e retornada
     * 
     * @param cpf CPF do paciente
     * @return Informações detalhadas do paciente ou da pessoa física
     */
    public PatientInfoDTO findByCpf(String cpf) {
        return patientSearchBusinessHandler.getByCpf(cpf);
    }

    /**
     * Busca informações do paciente baseado no Código da Carteira Nacional de Saúde
     * 
     * @param CNS Código da Carteira Nacional de Saúde
     * @return Informações detalhadas do paciente
     */
    public PatientInfoDTO findByCnsNumber(String cnsCode) {
        return patientSearchBusinessHandler.getByCnsNumber(cnsCode);
    }

    /**
     * Atualiza informações do paciente
     * 
     * @param id
     * @param entity
     * @return
     */
    public PatientInfoDTO update(Long id, PatientInfoDTO entity) {
        entity.setId(id);
        return updatePatientBusinessHandler.update(entity);
    }

    /**
     * Cadastra um novo paciente na aplicação
     * 
     * @param request Objeto de transferência com informações detalhadas do paciente
     * @return Informações do paciente pós insert com códigos de criação
     */
    public PatientInfoDTO persist(PatientInfoDTO request) {
        return createPatientBusinessHandler.persist(request);
    }

    /**
     * Atualiza as alergias do paciente
     * 
     * @param id
     * @param allergies
     */
    public void updateAllergies(Long patientId, PatientAllergiesDTO allergies) {
        logger.info("Iniciando atualização de alergidas para o paciente :: {}", patientId);
        logger.info("Limpando alergias anteriores...");
        patientAllergyDao.deleteByPatientId(patientId);

        if (allergies != null && !allergies.getDescriptions().isEmpty()) {
            logger.info("Total de Alergias à processar no paciente :: {}", allergies.getDescriptions().size());
            for (String allergy : allergies.getDescriptions()) {
                patientAllergyDao.saveAndFlush(new PatientAllergy(patientId, allergy));
            }
        }
        logger.info("Atualização das alergias do paciente concluída com sucesso!");

    }

}
