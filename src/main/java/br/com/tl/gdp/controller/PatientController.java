package br.com.tl.gdp.controller;

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

import br.com.tl.gdp.controller.filters.PatientFilters;
import br.com.tl.gdp.event.CreatedResourceEvent;
import br.com.tl.gdp.exception.ValidationException;
import br.com.tl.gdp.models.domain.Patient;
import br.com.tl.gdp.models.dto.PatientDTO;
import br.com.tl.gdp.models.dto.PatientInfoDTO;
import br.com.tl.gdp.models.enums.DocumentType;
import br.com.tl.gdp.service.PatientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * Classe PatientController.java
 *
 * @author <a href="carolexc@gmail.com">Caroline Aguiar</a>
 *
 * @since 8 de set de 2019
 */
@Api("Endpoint - Paciente")
@RestController
@RequestMapping(value = "/patient")
public class PatientController {

    @Autowired
    private PatientService service;

    @Autowired
    private ApplicationEventPublisher publisher;

    @ApiOperation(nickname = "patient-get", value = "Busca uma página de pacientes")
    @GetMapping
    @PreAuthorize("hasAnyRole('PACIENTE_LEITURA', 'ATENDIMENTO_ESCRITA', 'ATENDIMENTO_LEITURA', 'ADMIN')")
    public ResponseEntity<Page<PatientDTO>> findPage(PatientFilters filters) {
        Pageable pageSettings = PageRequest.of(filters.getPage(), filters.getSize(), Direction.valueOf(filters.getDirection()),
                                               filters.getOrderBy());
        return ResponseEntity.ok(service.getListOfPatientInfo(filters, pageSettings));
    }

    @ApiOperation(nickname = "patient-post", value = "Insere um novo paciente na aplicação")
    @PostMapping
    @PreAuthorize("hasAnyRole('PACIENTE_ESCRITA', 'ATENDIMENTO_ESCRITA', 'ADMIN')")
    public ResponseEntity<PatientInfoDTO> persist(@Validated @RequestBody(required = true) PatientInfoDTO newPatient,
                                                  HttpServletResponse response) {
        PatientInfoDTO createdPatient = service.persist(newPatient);

        publisher.publishEvent(new CreatedResourceEvent(this, response, createdPatient.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(createdPatient);

    }

    @ApiOperation(nickname = "patient-put", value = "Atualiza um paciente na aplicação")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('PACIENTE_ESCRITA', 'ATENDIMENTO_ESCRITA', 'ADMIN')")
    public ResponseEntity<PatientInfoDTO> update(@PathVariable("id") Long id,
                                                 @Validated @RequestBody(required = true) PatientInfoDTO patient,
                                                 HttpServletResponse response) {
        PatientInfoDTO createdResponsible = service.update(id, patient);

        return ResponseEntity.ok().body(createdResponsible);

    }

    @ApiOperation(nickname = "patient-get-id", value = "Busca um paciente baseado no identificador")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PACIENTE_LEITURA', 'ATENDIMENTO_ESCRITA', 'ATENDIMENTO_LEITURA', 'ADMIN')")
    public ResponseEntity<PatientInfoDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findByPateintId(id));
    }

    @ApiOperation(nickname = "patient-get-by-document", value = "Busca um paciente pelo documento")
    @GetMapping("{documentType}/{document}")
    @PreAuthorize("hasAnyRole('PACIENTE_LEITURA', 'PACIENTE_ESCRITA', 'ATENDIMENTO_ESCRITA', 'ATENDIMENTO_LEITURA', 'ADMIN')")
    public ResponseEntity<PatientInfoDTO> findByDocument(@PathVariable("documentType") DocumentType documentType,
                                                         @PathVariable("document") String document) {
        switch (documentType) {
            case CPF:
                return ResponseEntity.ok(service.findByCpf(document));
            case SUS:
                return ResponseEntity.ok(service.findBySusNumber(document));
            default:
                throw new ValidationException("Tipo de documento desconhecido, esperados: [CPF | SUS]");
        }
    }

    @ApiOperation(nickname = "patient-get-composite", value = "Busca um paciente baseado no identificador composto")
    @GetMapping("/{name}/{motherName}/{bornDate}")
    @PreAuthorize("hasAnyRole('PACIENTE_LEITURA', 'PACIENTE_ESCRITA', 'ATENDIMENTO_ESCRITA', 'ATENDIMENTO_LEITURA', 'ADMIN')")
    public ResponseEntity<List<Patient>> findByComposition(@PathVariable("name") String name,
                                                           @PathVariable("motherName") String motherName) {
        return ResponseEntity.ok(service.findByComposition(name, motherName));
    }

}
