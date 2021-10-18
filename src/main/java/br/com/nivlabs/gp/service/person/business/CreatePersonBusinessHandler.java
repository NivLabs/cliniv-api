package br.com.nivlabs.gp.service.person.business;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.enums.OperationType;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.Person;
import br.com.nivlabs.gp.models.dto.PersonInfoDTO;

/**
 * 
 * Componente específico para a criação de novos cadastros de Pessoa física
 *
 * @author viniciosarodrigues
 * @since 26-09-2021
 *
 */
@Component
public class CreatePersonBusinessHandler extends CreateOrUpdatePersonBusinessHandler {

    /**
     * Cria um novo cadastro de pessoa física
     * 
     * @param request
     * @return
     */
    public PersonInfoDTO create(PersonInfoDTO request) {
        if (request.getDocument() != null
                && personRepo.findByCpf(request.getDocument().getValue()).isPresent()) {
            throw new HttpException(HttpStatus.CONFLICT, "Já existe um cadastro de pessoa física com o CPF informado");
        }

        Person entity = new Person();
        parsePropertiesToEntity(request, entity, OperationType.CREATE);
        addressProcess(request, entity);
        logger.info("Criando novo cadastro de pessoa física");
        personRepo.saveAndFlush(entity);
        request.setId(entity.getId());

        documentsProcess(request, entity);

        logger.info("Cadastro realizada com sucesso!");

        return request;

    }

}
