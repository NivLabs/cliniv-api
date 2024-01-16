package br.com.nivlabs.cliniv.controller;

import java.net.URI;
import java.net.URISyntaxException;

import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nivlabs.cliniv.controller.filters.AllergyFilters;
import br.com.nivlabs.cliniv.models.dto.AllergyDTO;
import br.com.nivlabs.cliniv.models.dto.PatientAllergiesDTO;
import br.com.nivlabs.cliniv.service.alergy.AllergyService;
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
@Tag(name = "Alergias", description = "Endpoint - Operações com Alergias")
@RestController
@RequestMapping(value = "/allergy")
public class AllergyController extends BaseController<AllergyService> {

    @Autowired
    private Logger logger;

    @Operation(summary = "allergy-get", description = "Busca uma página de alergias")
    @GetMapping
    @PreAuthorize("hasAnyRole('PACIENTE_LEITURA', 'ATENDIMENTO_ESCRITA', 'ATENDIMENTO_LEITURA', 'ADMIN')")
    public ResponseEntity<Page<AllergyDTO>> findPage(AllergyFilters filters) {
        return ResponseEntity.ok(service.getPage(filters));
    }

    @Operation(summary = "allergies-patient-post", description = "Insere informações de alergias do paciente")
    @PostMapping("/patient/{id}")
    @PreAuthorize("hasAnyRole('PACIENTE_ESCRITA', 'ATENDIMENTO_ESCRITA', 'ADMIN')")
    public ResponseEntity<Void> savePatientAllergies(@PathVariable("id") Long id,
                                                     @RequestBody(required = true) PatientAllergiesDTO allergies,
                                                     HttpServletResponse response) {
        service.savePatientAllergies(id, allergies);

        URI uri = null;
        try {
            uri = new URI("/patient/".concat(String.valueOf(id)));

            return ResponseEntity.created(uri).build();
        } catch (URISyntaxException e) {
            logger.error("Falha ao criar a URI da da atualização de alergias do paciente, o processo continuará normalmente.", e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }
}
