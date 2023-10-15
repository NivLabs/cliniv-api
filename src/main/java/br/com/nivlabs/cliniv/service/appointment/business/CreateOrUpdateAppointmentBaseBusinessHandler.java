package br.com.nivlabs.cliniv.service.appointment.business;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.cliniv.ApplicationMain;
import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.Appointment;
import br.com.nivlabs.cliniv.models.domain.Patient;
import br.com.nivlabs.cliniv.models.domain.Responsible;
import br.com.nivlabs.cliniv.models.dto.AppointmentInfoDTO;
import br.com.nivlabs.cliniv.models.dto.PatientInfoDTO;
import br.com.nivlabs.cliniv.models.dto.ResponsibleInfoDTO;
import br.com.nivlabs.cliniv.repository.AppointmentRepository;
import br.com.nivlabs.cliniv.service.BaseBusinessHandler;
import br.com.nivlabs.cliniv.service.patient.PatientService;
import br.com.nivlabs.cliniv.service.responsible.ResponsibleService;

@Component
public abstract class CreateOrUpdateAppointmentBaseBusinessHandler implements BaseBusinessHandler {

    @Autowired
    private Logger logger;

    @Autowired
    protected ResponsibleService responsibleService;
    @Autowired
    protected PatientService patientService;

    @Autowired
    protected AppointmentRepository principalRepo;

    protected Appointment convertObject(AppointmentInfoDTO dto) {
        Appointment converted = new Appointment();
        converted.setId(dto.getId());
        converted.setAppointmentDateAndTime(dto.getSchedulingDateAndTime());
        converted.setAnnotation(dto.getAnnotation());
        converted.setStatus(dto.getStatus());
        converted.setPatient(new Patient(dto.getPatient().getId()));
        converted.setProfessional(new Responsible(dto.getProfessional().getId()));
        converted.setCreatedAt(LocalDateTime.now(ZoneId.of(ApplicationMain.AMERICA_SAO_PAULO)));

        return converted;
    }

    /**
     * Valida se a requisição é válida
     * 
     * @param id Identificador único do agendamento em casos de atualização
     * @param request Requisição d agendamento
     */
    protected void validateRequest(Long id, AppointmentInfoDTO request) {
        if (id != null) {
            Appointment appointment = principalRepo.findById(id)
                    .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND, "Agendamento não encontrado"));
            logger.warn("Agendamento localizado, prosseguindo com as validações :: Cod: {} | Paciente: {} | Profissional {}",
                        appointment.getId(), appointment.getPatient().getPerson().getFullName(),
                        appointment.getProfessional().getPerson().getFullName());
        }
        request.setId(id);
        checkPatient(request);
        checkProfessional(request);
        logger.info("Verificando data de criação do agendamento...");
        if (request.getCreatedAt() == null) {
            request.setCreatedAt(LocalDateTime.now());
            logger.warn("Data não encontrada... Inicializando com a data/hora atual do servidor :: {}", request.getCreatedAt());
        }
        if (request.getSchedulingDateAndTime() == null) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "Informe a data/hora à ser agendada");
        }

    }

    /**
     * Verifica se o profissional informado já está cadastrado
     * 
     * @param request Requisição de agendamento
     */
    private void checkProfessional(AppointmentInfoDTO request) {
        ResponsibleInfoDTO professional = request.getProfessional();
        logger.info("Verificando se o profissional já está cadastrado...");
        if (professional == null || professional.getId() == null) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "O identificador do profissional deve ser informado para o agendamento");
        }
        professional = responsibleService.findById(professional.getId());
        logger.info("Profissional encontrado :: Cod: {} | Nome: {}", professional.getId(), professional.getFullName());
        request.setProfessional(professional);

    }

    /**
     * Verifica se o paciente informado já está cadastrado
     * 
     * @param request Requisição de agendamento
     */
    private void checkPatient(AppointmentInfoDTO request) {
        PatientInfoDTO patient = request.getPatient();
        logger.info("Verificando se o paciente já está cadastrado...");
        if (patient == null || patient.getId() == null) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "O identificador do paciente deve ser informado para o agendamento");
        }
        patient = patientService.findByPatientId(patient.getId());
        logger.info("Paciente encontrado :: Cod: {} | Nome: {}", patient.getId(), patient.getFullName());
        request.setPatient(patient);
    }

    public abstract AppointmentInfoDTO execute(AppointmentInfoDTO request);

}
