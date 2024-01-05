package br.com.nivlabs.cliniv.controller;

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

import br.com.nivlabs.cliniv.controller.filters.AppointmentFilters;
import br.com.nivlabs.cliniv.models.dto.AppointmentInfoDTO;
import br.com.nivlabs.cliniv.models.dto.AppointmentsResponseDTO;
import br.com.nivlabs.cliniv.service.appointment.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Agenda", description = "Endpoint - Operações da Agenda")
@RestController
@RequestMapping(value = "/appointment")
public class AppointmentController extends BaseController<AppointmentService> {

    @Operation(summary = "appointment-get", description = "Busca informações de agendamentos baseados num filtro")
    @GetMapping
    @PreAuthorize("hasAnyRole('AGENDA_LEITURA', 'AGENDA_ESCRITA', 'ATENDIMENTO_ESCRITA', 'ATENDIMENTO_LEITURA', 'ADMIN')")
    public ResponseEntity<AppointmentsResponseDTO> getSchedulesByFilters(AppointmentFilters filters) {
        return ResponseEntity.ok(service.findByFilters(filters));
    }

    @Operation(summary = "appointment-get-id", description = "Busca um agendamento")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('AGENDA_ESCRITA', 'AGENDA_LEITURA', 'ATENDIMENTO_ESCRITA', 'ATENDIMENTO_LEITURA', 'ADMIN')")
    public ResponseEntity<AppointmentInfoDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @Operation(summary = "appointment-post", description = "Insere um novo agendamento na aplicação")
    @PostMapping
    @PreAuthorize("hasAnyRole('AGENDA_ESCRITA', 'ADMIN')")
    public ResponseEntity<AppointmentInfoDTO> create(@Validated @RequestBody(required = true) AppointmentInfoDTO appointment) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(appointment));
    }

    @Operation(summary = "appointment-put", description = "Atualiza agendamento existente na aplicação")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('AGENDA_ESCRITA', 'ADMIN')")
    public ResponseEntity<AppointmentInfoDTO> update(@PathVariable("id") Long id,
                                                     @Validated @RequestBody(required = true) AppointmentInfoDTO appointment) {
        return ResponseEntity.ok().body(service.update(id, appointment));
    }

}
