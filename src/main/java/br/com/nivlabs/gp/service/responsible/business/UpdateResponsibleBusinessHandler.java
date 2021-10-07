package br.com.nivlabs.gp.service.responsible.business;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.Responsible;
import br.com.nivlabs.gp.models.dto.PersonInfoDTO;
import br.com.nivlabs.gp.models.dto.ResponsibleInfoDTO;

/**
 * 
 * Componente responsálvel por atualiza informações do profissional / responsável
 * 
 * @author viniciosarodrigues
 * @since 27-09-2021
 *
 */
@Component
public class UpdateResponsibleBusinessHandler extends CreateOrUpdateResponsibleBusinessHandler {

    /**
     * Atualiza informações do profissional / responsável
     * 
     * @param responsibleInfo Informações atualizadas do profissional
     * @return Informações atualizadas do profissional
     */
    @Transactional
    public ResponsibleInfoDTO update(ResponsibleInfoDTO responsibleInfo) {
        logger.info("Inciando processo de atualização de profissional ou responsável...");
        Responsible responsibleEntity = responsibleRepo.findById(responsibleInfo.getId())
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                        String.format(RESPONSIBLE_NOT_FOUND, responsibleInfo.getId())));

        PersonInfoDTO personInfoToUpdate = new PersonInfoDTO();
        personInfoToUpdate.setId(responsibleEntity.getPerson().getId());
        parsePropertiesToPersonInfo(responsibleInfo, personInfoToUpdate);

        personService.update(personInfoToUpdate);

        parsePropertiesToEntity(responsibleInfo, responsibleEntity);
        handleSpecializations(responsibleInfo, responsibleEntity);
        responsibleRepo.saveAndFlush(responsibleEntity);
        return responsibleInfo;
    }

}
