package br.com.nivlabs.gp.service.patient.business;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.enums.DocumentType;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.Patient;
import br.com.nivlabs.gp.models.domain.Person;
import br.com.nivlabs.gp.models.dto.PatientInfoDTO;
import br.com.nivlabs.gp.models.dto.PersonInfoDTO;
import br.com.nivlabs.gp.util.StringUtils;

/**
 * 
 * Camada de negócio para a criação de pacientes
 *
 * @author viniciosarodrigues
 * @since 18-09-2021
 *
 */
@Component
public class CreatePatientBusinessHandler extends CreateOrUpdatePatientBusinessHandler {

    /**
     * Cadastra um novo paciente na aplicação
     * 
     * @param request Objeto de transferência com informações detalhadas do paciente
     * @return Informações do paciente pós insert com códigos de criação
     */
    @Transactional
    public PatientInfoDTO create(PatientInfoDTO request) {
        logger.info("Iniciando processo de cadastro de paciente...");
        PersonInfoDTO personInfo = getValidPerson(request);

        logger.info("Copiando as propriedades da requisição para o objeto de negócio...");
        parsePropertiesToPersonInfo(request, personInfo);

        if (personInfo.getId() != null) {
            personService.update(personInfo);
        } else {
            personService.create(personInfo);
        }

        Patient newPatient = new Patient();
        newPatient.setPerson(new Person(personInfo.getId()));
        newPatient.setCnsNumber(request.getCnsNumber());
        newPatient.setCreatedAt(LocalDateTime.now());

        handlePatientType(newPatient);
        handleHealthPlah(request, newPatient);
        logger.info("Salvando informações do paciente...");
        patientRepo.saveAndFlush(newPatient);

        request.setId(newPatient.getId());
        return request;
    }

    /**
     * Valida o cadastro informações da pessoa e retorna um objeto válido
     * 
     * @param request Objeto de transferência com informações do paciente
     * @return Entidade existente ou nova se não existir
     */
    private PersonInfoDTO getValidPerson(PatientInfoDTO request) {
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
                logger.info("Nenhum cadastro de paciente encontrado :: CPF da busca -> {}", request.getDocument().getValue());
                logger.info("Continuando cadastro de paciente...");
            } else {
                logger.error("Problema não esperado na verificação de existência de paciente :: ", e);
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
