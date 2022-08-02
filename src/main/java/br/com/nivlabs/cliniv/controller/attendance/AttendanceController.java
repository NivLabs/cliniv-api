package br.com.nivlabs.cliniv.controller.attendance;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nivlabs.cliniv.controller.BaseController;
import br.com.nivlabs.cliniv.controller.filters.AttendanceFilters;
import br.com.nivlabs.cliniv.event.CreatedResourceEvent;
import br.com.nivlabs.cliniv.models.dto.AttendanceDTO;
import br.com.nivlabs.cliniv.models.dto.CloseAttandenceDTO;
import br.com.nivlabs.cliniv.models.dto.MedicalRecordDTO;
import br.com.nivlabs.cliniv.models.dto.NewAttandenceDTO;
import br.com.nivlabs.cliniv.models.dto.NewAttendanceEventDTO;
import br.com.nivlabs.cliniv.service.attendance.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 
 * Classe AttendanceController.java
 *
 * @author <a href="carolexc@gmail.com">Caroline Aguiar</a>
 *
 * @since 8 de set de 2019
 */
@Tag(name = "Atendimento", description = "Endpoint - Atendimento")
@RestController()
@RequestMapping(value = "/attendance")
public class AttendanceController extends BaseController<AttendanceService> {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Operation(summary = "attendance-get", description = "Busca uma página de atendimentos")
    @GetMapping
    @PreAuthorize("hasAnyRole('ATENDIMENTO_ESCRITA', 'ATENDIMENTO_LEITURA', 'ADMIN')")
    public ResponseEntity<Page<AttendanceDTO>> findPage(AttendanceFilters filters) {
        Pageable pageSettings = PageRequest.of(filters.getPage(), filters.getSize(), Direction.valueOf(filters.getDirection()),
                                               filters.getOrderBy());
        return ResponseEntity.ok(service.getAttendancesPage(filters, pageSettings));
    }

    /**
     * Inicia um novo atendimento devolvendo um prontuário de visita ativa
     * 
     * @param newAttendance
     * @param response
     * @return
     */
    @Operation(summary = "attendance-post", description = "Inicia um novo atendimento na aplicação")
    @PostMapping
    @PreAuthorize("hasAnyRole('ATENDIMENTO_ESCRITA', 'ADMIN')")
    public ResponseEntity<MedicalRecordDTO> persist(@Validated @RequestBody(required = true) NewAttandenceDTO newAttendance,
                                                    HttpServletResponse response) {

        MedicalRecordDTO createdAttendance = service.createNewAttendance(newAttendance);

        publisher.publishEvent(new CreatedResourceEvent(this, response, createdAttendance.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(createdAttendance);

    }

    /**
     * Finaliza um atendimento ativo
     * 
     * @param newAttendance
     * @param response
     * @return
     */
    @Operation(summary = "attendance-put", description = "Finaliza um atendimento na aplicação")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ATENDIMENTO_ALTA', 'ADMIN')")
    public ResponseEntity<Void> closeAttendance(@PathVariable("id") Long id,
                                                @Validated @RequestBody(required = true) CloseAttandenceDTO request,
                                                HttpServletResponse response) {
        service.closeAttendance(id, request);
        return ResponseEntity.ok().build();

    }

    /**
     * Busca um atendimento ativo baseado no identificador do paciente
     * 
     * @param id
     * @return
     */
    @Operation(summary = "attendance-get-active", description = "Busca um prontuário ativo do paciente")
    @GetMapping("/actived/{id}/patient")
    @PreAuthorize("hasAnyRole('ATENDIMENTO_LEITURA', 'ATENDIMENTO_ESCRITA', 'ADMIN')")
    public ResponseEntity<MedicalRecordDTO> getActiveAttendance(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getActiveMedicalRecord(id));
    }

    /**
     * Busca o histórico de atendimentos do paciente
     * 
     * @param id
     * @param documentType
     * @param documentValue
     * @return
     */
    @Operation(summary = "attendance-get-with-filters", description = "Busca uma visita baseado em filtros")
    @GetMapping("/history/{id}/patient")
    @PreAuthorize("hasAnyRole('ATENDIMENTO_LEITURA', 'ATENDIMENTO_ESCRITA', 'ADMIN')")
    public ResponseEntity<List<AttendanceDTO>> getPatientAttendanceHistory(@PathVariable(name = "id", required = false) Long id) {
        return ResponseEntity.ok(service.getAttandenceByPatientId(id));
    }

    @Operation(summary = "attendance-get-id", description = "Busca um prontuário do atendimento ativo baseado no identificador")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ATENDIMENTO_LEITURA', 'ATENDIMENTO_ESCRITA', 'ADMIN')")
    public ResponseEntity<MedicalRecordDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findMedicalRecordByAttendanceId(id));
    }

    @Operation(summary = "attendance-event-post", description = "Insere um novo evento de atendimento")
    @PostMapping("/event")
    @PreAuthorize("hasAnyRole('RECEPCAO', 'MEDICO', 'ENFERMEIRO', 'ADMIN')")
    public ResponseEntity<Void> persist(
                                        @Validated @RequestBody(required = true) NewAttendanceEventDTO newAttendanceEvent,
                                        HttpServletResponse response) {
        service.createNewAttendanceEvent(newAttendanceEvent);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

}