package br.com.nivlabs.cliniv.service.person.business;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.cliniv.enums.OperationType;
import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.Person;
import br.com.nivlabs.cliniv.models.dto.PersonInfoDTO;

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
    public PersonInfoDTO update(PersonInfoDTO request) {

        if (request.getId() == null) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Identificador único do paciente obrigatório para atualização!");
        }
        logger.info("Iniciando atualização cadastra dos dados da pessoa física para o identificador :: {}",
                    request.getId());

        Person fetchedPerson = personRepo.findById(request.getId())
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
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
