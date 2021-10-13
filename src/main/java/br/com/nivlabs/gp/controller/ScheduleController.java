package br.com.nivlabs.gp.controller;

import java.util.List;

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

import br.com.nivlabs.gp.controller.filters.ScheduleFilters;
import br.com.nivlabs.gp.models.dto.ScheduleDTO;
import br.com.nivlabs.gp.models.dto.ScheduleInfoDTO;
import br.com.nivlabs.gp.service.schedule.ScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("Endpoint - Agendamento")
@RestController
@RequestMapping(value = "/schedule")
public class ScheduleController extends BaseController<ScheduleService> {

    @ApiOperation(nickname = "schedule-get", value = "Busca informações de agendamentos baseados num filtro")
    @GetMapping
    @PreAuthorize("hasAnyRole('AGENDA_LEITURA', 'AGENDA_ESCRITA', 'ATENDIMENTO_ESCRITA', 'ATENDIMENTO_LEITURA', 'ADMIN')")
    public ResponseEntity<List<ScheduleDTO>> getSchedulesByFilters(ScheduleFilters filters) {
        Pageable pageSettings = PageRequest.of(filters.getPage(), filters.getSize(),
                                               Direction.valueOf(filters.getDirection()), filters.getOrderBy());
        return ResponseEntity.ok(service.findByFilters(filters, pageSettings));
    }

    @ApiOperation(nickname = "schedule-get-id", value = "Busca um agendamento")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('AGENDA_ESCRITA', 'AGENDA_LEITURA', 'ATENDIMENTO_ESCRITA', 'ATENDIMENTO_LEITURA', 'ADMIN')")
    public ResponseEntity<ScheduleInfoDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @ApiOperation(nickname = "schedule-post", value = "Insere um novo agendamento na aplicação")
    @PostMapping
    @PreAuthorize("hasAnyRole('AGENDA_ESCRITA', 'ADMIN')")
    public ResponseEntity<ScheduleInfoDTO> create(@Validated @RequestBody(required = true) ScheduleInfoDTO schedule) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(schedule));
    }

    @ApiOperation(nickname = "schedule-put", value = "Atualiza agendamento existente na aplicação")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('AGENDA_ESCRITA', 'ADMIN')")
    public ResponseEntity<ScheduleInfoDTO> update(@PathVariable("id") Long id,
                                                  @Validated @RequestBody(required = true) ScheduleInfoDTO schedule) {
        return ResponseEntity.ok().body(service.update(id, schedule));
    }

}
