package br.com.nivlabs.gp.service.evolution.business;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.enums.EventType;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.Accommodation;
import br.com.nivlabs.gp.models.domain.Attendance;
import br.com.nivlabs.gp.models.domain.Evolution;
import br.com.nivlabs.gp.models.dto.AccommodationDTO;
import br.com.nivlabs.gp.models.dto.EvolutionInfoDTO;
import br.com.nivlabs.gp.models.dto.MedicalRecordDTO;
import br.com.nivlabs.gp.models.dto.NewAttendanceEventDTO;
import br.com.nivlabs.gp.repository.AccommodationRepository;
import br.com.nivlabs.gp.repository.EvolutionRepository;
import br.com.nivlabs.gp.service.BaseBusinessHandler;
import br.com.nivlabs.gp.service.attendance.AttendanceService;
import br.com.nivlabs.gp.util.SecurityContextUtil;

/**
 * 
 * Componente de negócio para criação de evoluções clínicas do paciente
 *
 * @author viniciosarodrigues
 * @since 26-09-2021
 *
 */
@Component
public class CreateEvolutionBusinessHandler implements BaseBusinessHandler {
    @Autowired
    private Logger logger;

    // Serviços
    @Autowired
    private AttendanceService attendanceService;

    // Repositórios
    @Autowired
    private AccommodationRepository accommodationRepo;
    @Autowired
    private EvolutionRepository evolutionRepo;

    /**
     * Insere uma nova evolução clínica do paciente
     * 
     * @param request
     */
    @Transactional
    public EvolutionInfoDTO create(EvolutionInfoDTO request) {
        logger.info("Iniciando a adição de uma evolução clínica ao sistema...\nBuscando pelo atendimento informado na requisição :: {}",
                    request.getAttendanceId());
        MedicalRecordDTO medicalRecord = attendanceService.findMedicalRecordByAttendanceId(request.getAttendanceId());

        logger.info("Atendimento encontrado para o paciente {}.", medicalRecord.getFullName());
        LocalDateTime currentTime = LocalDateTime.now();
        logger.info("Hora da solicitaçõ do evento de Evolução :: {}", currentTime);

        Evolution evolution = new Evolution();
        evolution.setAttendance(new Attendance(request.getAttendanceId()));
        evolution.setDescription(request.getDescription());
        evolution.setDatetime(currentTime);

        request.setDatetime(evolution.getDatetime());
        createAttendanceEvent(request, medicalRecord, SecurityContextUtil.getAuthenticatedUser().getUsername());

        logger.info("Inserindo evolução clínica na base de dados...");
        evolutionRepo.save(evolution);

        request.setId(evolution.getId());
        logger.info("Evolução clínica adicionada com sucesso :: Identificador :: {}", request.getId());

        return request;
    }

    /**
     * Cria o evento de atendimento para adição de Evolução clínica
     * 
     * @param evolution
     */
    @Transactional
    private void createAttendanceEvent(EvolutionInfoDTO request, MedicalRecordDTO medicalRecord, String userFromSession) {
        logger.info("Iniciando processo de criação de evento do atendimento para Evolução clínica");
        NewAttendanceEventDTO event = new NewAttendanceEventDTO();
        event.setAttendanceId(request.getAttendanceId());
        event.setEventDateTime(request.getDatetime());
        event.setEventType(EventType.EVOLUTION);
        setAccommodationIntoAttendanceEvent(request, medicalRecord, event);
        event.setObservations(request.getDescription());
        attendanceService.createNewAttendanceEvent(event);
        logger.info("Evento de Atendimento para Evolução clínica criado com sucesso!");

    }

    /**
     * Adiciona uma acomodação no evento do atendimento
     * 
     * @param request
     * @param medicalRecord
     * @param event
     */
    private void setAccommodationIntoAttendanceEvent(EvolutionInfoDTO request, MedicalRecordDTO medicalRecord,
                                                     NewAttendanceEventDTO event) {
        logger.info("Verificando acomodação do evento...");
        if (request.getAccommodationId() != null) {
            logger.info("A acomodação foi informada via requisição :: Identificador informado :: {} :: Iniciando uma busca pelo mesmo...",
                        request.getAccommodationId());
            Accommodation accommodation = accommodationRepo.findById(request.getAccommodationId())
                    .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                            String.format("Acomodação com código %s não encontrada", request.getAccommodationId())));
            logger.info("A acomodação informada na requisição foi encontrada e adicionada ao evento :: {}", accommodation.getDescription());
            event.setAccommodation(new AccommodationDTO(request.getAccommodationId()));
        } else if (medicalRecord.getLastAccommodation() != null) {
            logger.info("Nenhuma acomodação foi informada na requisição, buscando última acomodação do paciente...");
            AccommodationDTO currentAccommodation = medicalRecord.getLastAccommodation();
            logger.info("A última acomodação do paciente foi adicionada ao evento do atendimento (Evolução) :: Identificador {} | Descrição {}",
                        currentAccommodation.getId(),
                        currentAccommodation.getDescription());
            event.setAccommodation(new AccommodationDTO(currentAccommodation.getId()));
        } else {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Nenhuma acomodação foi informada na requisição e não há registro de acomodações para o paciente.");
        }
    }

}
