package br.com.nivlabs.gp.service.attendance.business;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.enums.EntryType;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.Accommodation;
import br.com.nivlabs.gp.models.domain.Attendance;
import br.com.nivlabs.gp.models.domain.AttendanceEvent;
import br.com.nivlabs.gp.models.domain.Patient;
import br.com.nivlabs.gp.models.domain.Responsible;
import br.com.nivlabs.gp.models.dto.MedicalRecordDTO;
import br.com.nivlabs.gp.models.dto.NewAttandenceDTO;
import br.com.nivlabs.gp.models.dto.PatientInfoDTO;
import br.com.nivlabs.gp.repository.AttendanceEventRepository;
import br.com.nivlabs.gp.repository.AttendanceRepository;
import br.com.nivlabs.gp.service.BaseBusinessHandler;
import br.com.nivlabs.gp.service.patient.PatientService;

/**
 * 
 * Camada de serviço para criação de um novo atendimento na aplicação
 *
 * @author viniciosarodrigues
 * @since 19-09-2021
 *
 */
@Component
public class CreateNewAttendanceBusinessHandler implements BaseBusinessHandler {

    @Autowired
    private Logger logger;
    @Autowired
    private AttendanceRepository attendanceRepo;
    @Autowired
    private AttendanceEventRepository attendanceEventRepo;
    @Autowired
    private PatientService patientService;
    @Autowired
    SearchAttendanceBusinessHandler searchAttendanceBusinessHandler;
    @Autowired
    SearchMedicalRecordBusinessHandler searchMedicalRecordBusinessHandler;

    /**
     * Realiza a criação de um novo atendimento à partir do DTO
     *
     * @param NewAttandenceDTO Novo atendimento
     * @return MedicalRecordDTO
     */
    public MedicalRecordDTO create(NewAttandenceDTO request) {
        logger.info("Iniciando processo de criação de um novo atendimento para o paciente :: ID: {}", request.getPatientId());
        MedicalRecordDTO attendance = null;
        try {
            logger.info("Checando se há algum atendimento ativo para o paciente em questão...");
            searchMedicalRecordBusinessHandler.getActiveMedicalRecord(request.getPatientId());
        } catch (HttpException e) {
            logger.info("Nenhum atendimento ativo encontrado, iniciando criação de um novo atendimento.");
            if (e.getStatus().equals(HttpStatus.NOT_FOUND)) {
                PatientInfoDTO savedPatient = patientService.findByPatientId(request.getPatientId());

                Attendance convertedAttendance = new Attendance();
                convertedAttendance.setId(null);
                convertedAttendance.setReasonForEntry(request.getEntryCause());
                convertedAttendance.setLevel(request.getLevel());
                convertedAttendance.setPatient(new Patient(savedPatient.getId()));
                convertedAttendance.setCurrentAccommodation(new Accommodation(request.getAccommodationId(), null, null, null));

                switch (request.getEventType()) {
                    case MEDICAL_EMERGENCY:
                        convertedAttendance.setEntryType(EntryType.EMERGENCY);
                        break;
                    case MEDICAL_APPOINTMENT:
                        convertedAttendance.setEntryType(EntryType.CLINICAL);
                        break;
                    case REMOTE_MEDICAL_APPOINTMENT:
                        convertedAttendance.setEntryType(EntryType.REMOTE);
                        break;
                    default:
                        convertedAttendance.setEntryType(EntryType.GENERIC);
                }

                convertedAttendance = attendanceRepo.save(convertedAttendance);

                createEntryEvent(convertedAttendance, request);
                attendance = searchMedicalRecordBusinessHandler.getActiveMedicalRecord(request.getPatientId());

                return attendance;
            }
        }

        throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, String
                .format("O paciente de código %s já possui um atendimento ativo, favor realizar a alta do mesmo para iniciar um novo.",
                        request.getPatientId()));

    }

    /**
     * Cria evento de entrada
     *
     * @param convertedAtendance
     * @param request
     */
    private void createEntryEvent(Attendance convertedAtendance, NewAttandenceDTO request) {
        AttendanceEvent entryEvent = new AttendanceEvent();
        entryEvent.setEventDateTime(convertedAtendance.getEntryDateTime());
        entryEvent.setTitle(convertedAtendance.getReasonForEntry());
        entryEvent.setAttendance(new Attendance(convertedAtendance.getId()));
        entryEvent.setAccommodation(convertedAtendance.getCurrentAccommodation());

        if (request.getResponsibleId() != null)
            // Verificar Responsável
            entryEvent.setResponsible(new Responsible(request.getResponsibleId()));

        entryEvent.setEventType(request.getEventType());

        attendanceEventRepo.save(entryEvent);
    }
}
