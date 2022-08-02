package br.com.nivlabs.cliniv.controller.patient;

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
import br.com.nivlabs.cliniv.controller.filters.PatientFilters;
import br.com.nivlabs.cliniv.enums.DocumentType;
import br.com.nivlabs.cliniv.event.CreatedResourceEvent;
import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.dto.PatientDTO;
import br.com.nivlabs.cliniv.models.dto.PatientInfoDTO;
import br.com.nivlabs.cliniv.service.patient.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 
 * Classe PatientController.java
 *
 * @author <a href="carolexc@gmail.com">Caroline Aguiar</a>
 *
 * @since 8 de set de 2019
 */
@Tag(name = "Paciente", description = "Endpoint - Operações com Paciente")
@RestController
@RequestMapping(value = "/patient")
public class PatientController extends BaseController<PatientService> {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Operation(summary = "patient-get", description = "Busca uma página de pacientes")
    @GetMapping
    @PreAuthorize("hasAnyRole('PACIENTE_LEITURA', 'ATENDIMENTO_ESCRITA', 'ATENDIMENTO_LEITURA', 'ADMIN')")
    public ResponseEntity<Page<PatientDTO>> findPage(PatientFilters filters) {
        Pageable pageSettings = PageRequest.of(filters.getPage(), filters.getSize(), Direction.valueOf(filters.getDirection()),
                                               filters.getOrderBy());
        return ResponseEntity.ok(service.getPage(filters, pageSettings));
    }

    @Operation(summary = "patient-post", description = "Insere um novo paciente na aplicação")
    @PostMapping
    @PreAuthorize("hasAnyRole('PACIENTE_ESCRITA', 'ATENDIMENTO_ESCRITA', 'ADMIN')")
    public ResponseEntity<PatientInfoDTO> persist(@Validated @RequestBody(required = true) PatientInfoDTO newPatient,
                                                  HttpServletResponse response) {
        PatientInfoDTO createdPatient = service.persist(newPatient);

        publisher.publishEvent(new CreatedResourceEvent(this, response, createdPatient.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(createdPatient);

    }

    @Operation(summary = "patient-put", description = "Atualiza um paciente na aplicação")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('PACIENTE_ESCRITA', 'ATENDIMENTO_ESCRITA', 'ADMIN')")
    public ResponseEntity<PatientInfoDTO> update(@PathVariable("id") Long id,
                                                 @Validated @RequestBody(required = true) PatientInfoDTO patient) {
        return ResponseEntity.ok().body(service.update(id, patient));
    }

    @Operation(summary = "patient-get-id", description = "Busca um paciente baseado no identificador")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PACIENTE_LEITURA', 'ATENDIMENTO_ESCRITA', 'ATENDIMENTO_LEITURA', 'ADMIN')")
    public ResponseEntity<PatientInfoDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findByPatientId(id));
    }

    @Operation(summary = "patient-get-by-document", description = "Busca um paciente pelo documento")
    @GetMapping("{documentType}/{document}")
    @PreAuthorize("hasAnyRole('PACIENTE_LEITURA', 'PACIENTE_ESCRITA', 'ATENDIMENTO_ESCRITA', 'ATENDIMENTO_LEITURA', 'ADMIN')")
    public ResponseEntity<PatientInfoDTO> findByDocument(@PathVariable("documentType") DocumentType documentType,
                                                         @PathVariable("document") String document) {
        switch (documentType) {
            case CPF:
                return ResponseEntity.ok(service.findByCpf(document));
            case SUS:
                return ResponseEntity.ok(service.findByCnsNumber(document));
            default:
                throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "Tipo de documento desconhecido, esperados: [CPF | SUS]");
        }
    }
}
