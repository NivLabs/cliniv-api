package br.com.nivlabs.gp.service.alergy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.controller.filters.AllergyFilters;
import br.com.nivlabs.gp.models.dto.AllergyDTO;
import br.com.nivlabs.gp.models.dto.PatientAllergiesDTO;
import br.com.nivlabs.gp.service.BaseService;
import br.com.nivlabs.gp.service.alergy.business.CreateAllergyBusinessHandler;
import br.com.nivlabs.gp.service.alergy.business.SearchAlergyBusinessHandler;
import br.com.nivlabs.gp.service.patient.PatientService;

/**
 * Camada de serviços para Alergias
 * 
 *
 * @author viniciosarodrigues
 * @since 16-09-2021
 *
 */
@Service
public class AllergyService implements BaseService {

    @Autowired
    private PatientService patientService;

    @Autowired
    CreateAllergyBusinessHandler createAllergyBusinessHandler;
    @Autowired
    SearchAlergyBusinessHandler searchAlergyBusinessHandler;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Salva as alterações de alergias do paciente
     * 
     * @param patientId
     * @param allergies
     */
    public void savePatientAllergies(Long patientId, PatientAllergiesDTO allergies) {
        if (allergies != null && allergies.getDescriptions() != null && !allergies.getDescriptions().isEmpty()) {
            logger.info("Iniciando processamento de alergias na base de alergias :: Total de alergias informadas -> {}",
                        allergies.getDescriptions().size());
            allergies.getDescriptions().forEach(this::createIfNotExists);
        } else {
            logger.info("Não há alergias para processar, enviando exclusão de alergias do paciente");
        }

        patientService.updateAllergies(patientId, allergies);
    }

    /**
     * Cria as alergias que não existem na base de dados de alergias
     * 
     * @param allergyDescription
     */
    public void createIfNotExists(String allergyDescription) {
        createAllergyBusinessHandler.createIfNotExists(allergyDescription);
    }

    public Page<AllergyDTO> getPage(AllergyFilters filters, Pageable pageSettings) {
        return searchAlergyBusinessHandler.getPage(filters, pageSettings);
    }

}
