package br.com.nivlabs.gp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.controller.filters.AllergyFilters;
import br.com.nivlabs.gp.models.domain.Allergy;
import br.com.nivlabs.gp.models.dto.AllergyDTO;
import br.com.nivlabs.gp.models.dto.PatientAllergiesDTO;
import br.com.nivlabs.gp.repository.AllergyRepository;

@Service
public class AllergyService implements GenericService<Allergy, Long> {

    @Autowired
    private AllergyRepository principalRepository;

    @Autowired
    private PatientService patientService;

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
        if (!principalRepository.findByDescriptionIgnoreCase(allergyDescription).isPresent()) {
            logger.info(allergyDescription.concat(" não existe na base, persistindo a mesma."));
            principalRepository.save(new Allergy(allergyDescription));
        }
    }

    public Page<AllergyDTO> getPage(AllergyFilters filters, Pageable pageSettings) {
        return principalRepository.resumedList(filters, pageSettings);
    }

}
