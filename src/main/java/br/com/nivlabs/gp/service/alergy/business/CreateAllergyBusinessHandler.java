package br.com.nivlabs.gp.service.alergy.business;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.models.domain.Allergy;
import br.com.nivlabs.gp.repository.AllergyRepository;
import br.com.nivlabs.gp.service.BaseBusinessHandler;
import br.com.nivlabs.gp.util.StringUtils;

/**
 * 
 * Classe de negócio para criação de alergia
 *
 * @author viniciosarodrigues
 * @since 16-09-2021
 *
 */
@Component
public class CreateAllergyBusinessHandler implements BaseBusinessHandler {

    @Autowired
    private AllergyRepository allergyRepository;

    @Autowired
    private Logger logger;

    /**
     * Cria as alergias que não existem na base de dados de alergias
     * 
     * @param allergyDescription
     */
    public void createIfNotExists(String allergyDescription) {
        if (!StringUtils.isNullOrEmpty(allergyDescription)
                && !allergyRepository.findByDescriptionIgnoreCase(allergyDescription).isPresent()) {
            logger.info("Alergia a '{}' não existe na base de alergias, persistindo nova alergia.", allergyDescription);
            allergyRepository.save(new Allergy(allergyDescription));
        } else {
            logger.warn("A alergia informada está sem descrição, ignorando processo...");
        }
    }

}
