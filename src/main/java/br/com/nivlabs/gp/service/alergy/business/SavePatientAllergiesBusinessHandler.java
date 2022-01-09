package br.com.nivlabs.gp.service.alergy.business;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.models.dto.PatientAllergiesDTO;
import br.com.nivlabs.gp.service.BaseBusinessHandler;
import br.com.nivlabs.gp.service.patient.PatientService;

/**
 * 
 * Componente de negócio para criação de alergias e relacionamento com paciente
 *
 * @author viniciosarodrigues
 * @since 19-09-2021
 *
 */
@Component
public class SavePatientAllergiesBusinessHandler implements BaseBusinessHandler {

    @Autowired
    Logger logger;

    @Autowired
    private PatientService patientService;
    @Autowired
    CreateAllergyBusinessHandler createAllergyBusinessHandler;

    /**
     * Salva as alterações de alergias do paciente
     * 
     * @param patientId Identificador único do paciente
     * @param allergies Alergias à serem processadas e salvas no paciente
     */
    public void save(Long patientId, PatientAllergiesDTO allergies) {
        if (allergies != null && allergies.getDescriptions() != null && !allergies.getDescriptions().isEmpty()) {
            logger.info("Iniciando processamento de alergias na base de alergias :: Total de alergias informadas -> {}",
                        allergies.getDescriptions().size());
            allergies.getDescriptions().forEach(createAllergyBusinessHandler::createIfNotExists);
        } else {
            logger.info("Não há alergias para processar, enviando exclusão de alergias do paciente");
        }

        patientService.updateAllergies(patientId, allergies);
    }

}
