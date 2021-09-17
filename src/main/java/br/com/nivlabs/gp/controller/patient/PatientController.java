package br.com.nivlabs.gp.controller.patient;

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

import br.com.nivlabs.gp.controller.filters.PatientFilters;
import br.com.nivlabs.gp.enums.DocumentType;
import br.com.nivlabs.gp.event.CreatedResourceEvent;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.dto.PatientDTO;
import br.com.nivlabs.gp.models.dto.PatientInfoDTO;
import br.com.nivlabs.gp.service.patient.PatientService;
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
                                                 @Validated @RequestBody(required = true) PatientInfoDTO patient) {
        return ResponseEntity.ok().body(service.update(id, patient));
    }

    @ApiOperation(nickname = "patient-get-id", value = "Busca um paciente baseado no identificador")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PACIENTE_LEITURA', 'ATENDIMENTO_ESCRITA', 'ATENDIMENTO_LEITURA', 'ADMIN')")
    public ResponseEntity<PatientInfoDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findByPatientId(id));
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
                throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "Tipo de documento desconhecido, esperados: [CPF | SUS]");
        }
    }
}
