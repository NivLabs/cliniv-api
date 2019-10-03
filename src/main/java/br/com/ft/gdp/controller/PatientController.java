package br.com.ft.gdp.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ft.gdp.event.CreatedResourceEvent;
import br.com.ft.gdp.models.domain.Patient;
import br.com.ft.gdp.models.dto.NewOrUpdatePatientDTO;
import br.com.ft.gdp.models.dto.PatientDTO;
import br.com.ft.gdp.models.enums.DocumentType;
import br.com.ft.gdp.service.PatientService;
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
    @PreAuthorize("hasAnyRole('COMUM', 'ADMIN')")
    public ResponseEntity<Page<Patient>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                  @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
                                                  @RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
                                                  @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        Pageable pageSettings = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return ResponseEntity.ok(service.searchEntityPage(pageSettings));
    }

    @ApiOperation(nickname = "responsible-post", value = "Insere um novo paciente na aplicação")
    @PostMapping
    @PreAuthorize("hasAnyRole('RECEPCAO', 'MEDICO', 'ENFERMEIRO', 'ADMIN')")
    public ResponseEntity<PatientDTO> persist(@Validated @RequestBody(required = true) NewOrUpdatePatientDTO newPatient,
                                              HttpServletResponse response) {
        Patient createdPatient = service.persist(newPatient.getPatientDomainFromDTO());

        publisher.publishEvent(new CreatedResourceEvent(this, response, createdPatient.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(createdPatient.getPatientDTOFromDomain());

    }

    @ApiOperation(nickname = "responsible-put", value = "Atualiza um paciente na aplicação")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('RECEPCAO', 'MEDICO', 'ENFERMEIRO', 'ADMIN')")
    public ResponseEntity<PatientDTO> update(@PathVariable("id") Long id,
                                             @Validated @RequestBody(required = true) NewOrUpdatePatientDTO patient,
                                             HttpServletResponse response) {
        Patient createdResponsible = service.update(id, patient.getPatientDomainFromDTO());

        return ResponseEntity.ok().body(createdResponsible.getPatientDTOFromDomain());

    }

    @ApiOperation(nickname = "Patient-get-id", value = "Busca um paciente baseado no identificador")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('RECEPCAO', 'MEDICO', 'ENFERMEIRO', 'ADMIN')")
    public ResponseEntity<Patient> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @ApiOperation(nickname = "Patient-get-document", value = "Busca um paciente pelo documento")
    @GetMapping("{documentType}/{document}")
    @PreAuthorize("hasAnyRole('RECEPCAO', 'MEDICO', 'ENFERMEIRO', 'ADMIN')")
    public ResponseEntity<PatientDTO> findByCpf(@PathVariable("documentType") DocumentType documentType,
                                                @PathVariable("document") String document) {
        if (documentType.equals(DocumentType.CPF))
            return ResponseEntity.ok(service.findByCpf(document).getPatientDTOFromDomain());
        else
            return ResponseEntity.ok(service.findByRg(document).getPatientDTOFromDomain());
    }

}
