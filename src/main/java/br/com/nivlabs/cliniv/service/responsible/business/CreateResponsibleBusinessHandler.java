package br.com.nivlabs.cliniv.service.responsible.business;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.cliniv.enums.DocumentType;
import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.Person;
import br.com.nivlabs.cliniv.models.domain.Responsible;
import br.com.nivlabs.cliniv.models.dto.PersonInfoDTO;
import br.com.nivlabs.cliniv.models.dto.ResponsibleInfoDTO;
import br.com.nivlabs.cliniv.util.StringUtils;

/**
 * 
 * Componente específico para criação de profissional / responsável
 *
 * @author viniciosarodrigues
 * @since 27-09-2021
 *
 */
@Component
public class CreateResponsibleBusinessHandler extends CreateOrUpdateResponsibleBusinessHandler {

    /**
     * Cadastra um novo profissional na aplicação
     * 
     * @param request Objeto de transferência com informações detalhadas do profissional
     * @return Informações do profissional pós insert com códigos de criação
     */
    public ResponsibleInfoDTO create(ResponsibleInfoDTO request) {
        logger.info("Inciando processo de atualização de profissional ou responsável...");
        PersonInfoDTO personInfo = getValidPerson(request);

        logger.info("Copiando as propriedades da requisição para o objeto de negócio...");
        parsePropertiesToPersonInfo(request, personInfo);

        if (personInfo.getId() != null) {
            personService.update(personInfo);
        } else {
            personService.create(personInfo);
        }

        Responsible responsibleEntity = new Responsible();
        parsePropertiesToEntity(request, responsibleEntity);
        handleSpecializations(request, responsibleEntity);
        responsibleEntity.setCreatedAt(LocalDateTime.now());
        responsibleEntity.setPerson(new Person(personInfo.getId()));
        responsibleRepo.saveAndFlush(responsibleEntity);

        request.setId(responsibleEntity.getId());
        return request;
    }

    /**
     * Realiza a checagem da existência do paciente para o CPF informado
     * 
     * @param cpf CPF do paciente / pessoa física
     * @param id Identificador do paciente
     */
    protected void patientCheckIfExistsByCpf(String cpf, Long id) {
        logger.info("Verificando se já há cadastro de profissional na base de dados :: CPF da busca -> {}", cpf);
        ResponsibleInfoDTO responsibleInfo = searchResponsibleBusiness.byCPF(cpf);
        if (responsibleInfo != null && responsibleInfo.getId() != null && !responsibleInfo.getId().equals(id)) {
            logger.warn("Profissional com o CPF {} já cadastrado.", cpf);
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                    String.format("Profissional com o CPF informado já está cadastrado, não é possível realizar um outro cadastro com o mesmo CPF(%s).",
                                  cpf));
        }
    }

    /**
     * Valida o cadastro informações da pessoa e retorna um objeto válido
     * 
     * @param request Objeto de transferência com informações do paciente
     * @return Entidade existente ou nova se não existir
     */
    private PersonInfoDTO getValidPerson(ResponsibleInfoDTO request) {
        PersonInfoDTO validPerson = new PersonInfoDTO();
        if (request.getDocument() != null && request.getDocument().getValue() != null
                && request.getDocument().getType() != DocumentType.CPF) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Tipo do documento inválido, informe um documento válido.");
        }

        try {
            if (request.getDocument() != null && !StringUtils.isNullOrEmpty(request.getDocument().getValue()))
                patientCheckIfExistsByCpf(request.getDocument().getValue(), request.getId());
        } catch (HttpException e) {
            if (e.getStatus() == HttpStatus.NOT_FOUND) {
                logger.info("Nenhum cadastro de profissional encontrado :: CPF da busca -> {}", request.getDocument().getValue());
                logger.info("Continuando cadastro de profissional...");
            } else {
                logger.error("Problema não esperado na verificação de existência de profissional :: ", e);
                throw e;
            }
        }

        try {
            if (request.getDocument() != null && !StringUtils.isNullOrEmpty(request.getDocument().getValue())) {
                logger.info("Verificando se já existe um cadastro anexado ao documento informado...");
                validPerson = personService.findByCpf(request.getDocument().getValue());
            }
        } catch (HttpException e) {
            logger.info(
                        "Nenhum cadastro encontrado :: Criando um novo cadastro de Pessoa no documento :: TIPO: {} | VALOR: {}",
                        request.getDocument().getType(), request.getDocument().getValue());

        }
        return validPerson;
    }
}
