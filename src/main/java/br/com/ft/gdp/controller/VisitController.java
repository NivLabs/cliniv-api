package br.com.ft.gdp.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ft.gdp.event.CreatedResourceEvent;
import br.com.ft.gdp.exception.ValidationException;
import br.com.ft.gdp.models.dto.NewPatientVisitDTO;
import br.com.ft.gdp.models.dto.VisitDTO;
import br.com.ft.gdp.models.dto.VisitInfoDTO;
import br.com.ft.gdp.models.enums.DocumentType;
import br.com.ft.gdp.service.VisitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * Classe VisitController.java
 *
 * @author <a href="carolexc@gmail.com">Caroline Aguiar</a>
 *
 * @since 8 de set de 2019
 */
@Api("Endpoint - Visita")
@RestController
@RequestMapping(value = "/visit")
public class VisitController {

    @Autowired
    private VisitService service;

    @Autowired
    private ApplicationEventPublisher publisher;

    @ApiOperation(nickname = "visit-post", value = "Insere uma nova visita na aplicação")
    @PostMapping
    @PreAuthorize("hasAnyRole('RECEPCAO', 'MEDICO', 'ENFERMEIRO', 'ADMIN')")
    public ResponseEntity<VisitInfoDTO> persist(@Validated @RequestBody(required = true) NewPatientVisitDTO visit,
                                                HttpServletResponse response) {

        VisitInfoDTO createdVisit = service.persistNewVisit(visit);

        publisher.publishEvent(new CreatedResourceEvent(this, response, createdVisit.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(createdVisit);

    }

    @ApiOperation(nickname = "visit-put-exit", value = "Atualiza hora de saída da visita na aplicação")
    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('RECEPCAO', 'MEDICO', 'ENFERMEIRO', 'ADMIN')")
    public ResponseEntity<Void> updateVisitToEnd(@PathVariable("id") Long id) {
        service.closeVisit(id);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(nickname = "visit-get-active", value = "Busca a visita ativa do paciente")
    @GetMapping("/actived/{id}/patient")
    @PreAuthorize("hasAnyRole('RECEPCAO', 'MEDICO', 'ENFERMEIRO', 'ADMIN')")
    public ResponseEntity<VisitInfoDTO> getActiveVisit(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getActiveVisit(id));
    }

    @ApiOperation(nickname = "visit-get-with-filters", value = "Busca uma visita baseado em filtros")
    @GetMapping
    @PreAuthorize("hasAnyRole('RECEPCAO', 'MEDICO', 'ENFERMEIRO', 'ADMIN')")
    public ResponseEntity<List<VisitDTO>> findWithFilters(@RequestParam(name = "patientId", required = false) Long patientId,
                                                          @RequestParam(name = "documentType", required = false) DocumentType documentType,
                                                          @RequestParam(name = "documentValue", required = false) String documentValue) {
        List<VisitDTO> returnList = null;
        if (patientId != null && documentValue != null) {
            throw new ValidationException(
                    "Se você informar o identificador do paciente, você não pode informar o documento do Paciente. Se você informar o documento do paciente, você não pode informar o identificador do paciente.");
        } else if (patientId != null) {
            returnList = service.getVisitsByPatientId(patientId);
        } else if (documentType != null && documentValue == null) {
            throw new ValidationException(String.format("Você está filtrando por %s mas não informou o valor do documento.", documentType));
        } else if (documentType == null && documentValue != null) {
            throw new ValidationException(
                    String.format("Passou o valor (%s) do documento mas não especificou o tipo do mesmo.", documentValue));
        } else {
            returnList = service.findByDocument(documentType, documentValue);
        }
        return ResponseEntity.ok(returnList);
    }

    @ApiOperation(nickname = "visit-get-id", value = "Busca uma visita baseado no identificador")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('RECEPCAO', 'MEDICO', 'ENFERMEIRO', 'ADMIN')")
    public ResponseEntity<VisitInfoDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findInfoById(id));
    }

}
