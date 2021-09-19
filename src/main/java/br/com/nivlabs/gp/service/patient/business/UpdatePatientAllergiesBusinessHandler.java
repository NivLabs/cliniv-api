package br.com.nivlabs.gp.service.patient.business;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.models.domain.PatientAllergy;
import br.com.nivlabs.gp.models.dto.PatientAllergiesDTO;
import br.com.nivlabs.gp.repository.PatientAllergyRepository;
import br.com.nivlabs.gp.service.BaseBusinessHandler;

@Component
public class UpdatePatientAllergiesBusinessHandler implements BaseBusinessHandler {

    @Autowired
    private Logger logger;

    @Autowired
    private PatientAllergyRepository patientAllergyDao;

    /**
     * Atualiza as alergias do paciente
     * 
     * @param patientId Identificador único do paciente
     * @param request Requisição com informações das alergias do paciente
     */
    public void update(Long patientId, PatientAllergiesDTO request) {
        logger.info("Iniciando atualização de alergidas para o paciente :: {}", patientId);
        logger.info("Limpando alergias anteriores...");
        patientAllergyDao.deleteByPatientId(patientId);

        if (request != null && !request.getDescriptions().isEmpty()) {
            logger.info("Total de Alergias à processar no paciente :: {}", request.getDescriptions().size());
            for (String allergy : request.getDescriptions()) {
                patientAllergyDao.saveAndFlush(new PatientAllergy(patientId, allergy));
            }
        }
        logger.info("Atualização das alergias do paciente concluída com sucesso!");
    }

}
