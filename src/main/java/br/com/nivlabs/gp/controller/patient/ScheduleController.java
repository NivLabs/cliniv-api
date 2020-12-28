package br.com.nivlabs.gp.controller.patient;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nivlabs.gp.controller.filters.ScheduleFilters;
import br.com.nivlabs.gp.models.dto.ScheduleInfoDTO;
import br.com.nivlabs.gp.service.ScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("Endpoint - Agendamento")
@RestController
@RequestMapping(value = "/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService principalService;

    @ApiOperation(nickname = "schedule-get", value = "Busca informações de agendamentos baseados num filtro")
    @GetMapping
    @PreAuthorize("hasAnyRole('AGENDA_LEITURA', 'PACIENTE_LEITURA', 'ATENDIMENTO_ESCRITA', 'ATENDIMENTO_LEITURA', 'ADMIN')")
    public ResponseEntity<List<ScheduleInfoDTO>> getSchedulesByFilters(ScheduleFilters filters) {
        return ResponseEntity.ok(principalService.findByFilters(filters));
    }

}
