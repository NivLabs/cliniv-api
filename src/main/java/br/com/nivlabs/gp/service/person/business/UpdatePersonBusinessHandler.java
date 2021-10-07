package br.com.nivlabs.gp.service.person.business;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.enums.OperationType;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.Person;
import br.com.nivlabs.gp.models.dto.PersonInfoDTO;

/**
 * Componente específico para atualização de paciente
 * 
 * @author viniciosarodrigues
 * @since 26-09-2021
 *
 */
@Component
public class UpdatePersonBusinessHandler extends CreateOrUpdatePersonBusinessHandler {

    /**
     * Atualiza os dados cadastrais de pessoa física
     * 
     * @param request Requisição com dados cadastrais novos para atualização
     * @return Dados cadastrais atualizados
     */
    @Transactional
    public PersonInfoDTO update(PersonInfoDTO request) {

        if (request.getId() == null) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "Identificador único do paciente obrigatório para atualização!");
        }
        logger.info("Iniciando atualização cadastra dos dados da pessoa física para o identificador :: {}", request.getId());

        Person fetchedPerson = personRepo.findById(request.getId()).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                "Pessoa com o identificador " + request.getId() + " não encontrado"));

        logger.info("Cadastro de pessoa física encontrado :: Nome: {}", fetchedPerson.getFullName());

        parsePropertiesToEntity(request, fetchedPerson, OperationType.UPDATE);
        addressProcess(request, fetchedPerson);
        logger.info("Salvando novas informações na tabela de pessoa física");
        personRepo.saveAndFlush(fetchedPerson);

        documentsProcess(request, fetchedPerson);

        logger.info("Atualização realizada com sucesso!");

        return request;
    }

}
