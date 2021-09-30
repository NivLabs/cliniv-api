package br.com.nivlabs.gp.service.patient.business;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.enums.DocumentType;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.Patient;
import br.com.nivlabs.gp.models.dto.PatientInfoDTO;
import br.com.nivlabs.gp.models.dto.PersonInfoDTO;
import br.com.nivlabs.gp.util.DocumentValidator;

/**
 * Camada de negócio para atualização dos dados cadastrais de paciente
 * 
 *
 * @author viniciosarodrigues
 * @since 18-09-2021
 *
 */
@Component
public class UpdatePatientBusinessHandler extends CreateOrUpdatePatientBusinessHandler {

    /**
     * Atualiza informações de um paciente já existente na base de dados
     * 
     * @param id Identificador único do paciente
     * @param request Objeto de transferência com informações do paciente
     * @return Informações atualizadas do paciente
     */
    @Transactional
    public PatientInfoDTO update(PatientInfoDTO request) {
        logger.info("Iniciando processo de atualização cadastral do paciente :: ID {}", request.getId());
        if (request.getDocument() != null
                && (request.getDocument().getValue() != null && !DocumentValidator.isValidCPF(request.getDocument().getValue())
                        && request.getDocument().getType() != DocumentType.CPF)) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Tipo do documento inválido, informe um documento válido.");
        }

        Patient patientEntity = patientRepo.findById(request.getId()).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                String.format("Paciente com o identificador %s não encontrado", request.getId())));

        logger.info("Atualizando informações do paciente :: {} | {}", request.getId(),
                    request.getFullName() == null ? "Nome não informado" : request.getFullName());

        checkCnsCode(request, patientEntity);

        try {
            if (request.getDocument() != null && request.getDocument().getValue() != null)
                patientCheckIfExistsByCpf(request.getDocument().getValue(), patientEntity.getId());
        } catch (HttpException e) {
            if (e.getStatus() == HttpStatus.NOT_FOUND) {
                logger.info("O CPF {} está disponível para uso...", request.getDocument().getValue());
            } else {
                throw e;
            }
        }

        PersonInfoDTO personInfoToUpdate = new PersonInfoDTO();
        parsePropertiesToPersonInfo(request, personInfoToUpdate);
        personService.update(personInfoToUpdate);

        parsePropertiesToEntity(request, patientEntity);
        handlePatientType(patientEntity);
        handleHealthPlah(request, patientEntity);
        logger.info("Salvando informações do paciente...");
        patientRepo.saveAndFlush(patientEntity);

        logger.info("Cadastro do paciente :: {} | {} :: atualizado com sucesso!", request.getId(), request.getFullName());

        return request;
    }

}
