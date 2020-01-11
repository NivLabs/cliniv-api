package br.com.ft.gdp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ft.gdp.models.dto.SpecialityDTO;
import br.com.ft.gdp.models.dto.SpecialityInfoDTO;
import br.com.ft.gdp.service.SpecialityService;
import io.swagger.annotations.Api;

/**
 * Classe SpecialityController.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 6 de jan de 2020
 */
@Api("Endpoint - Especialidade")
@RestController
@RequestMapping(value = "/speciality")
public class SpecialityController {

    @Autowired
    private SpecialityService specService;

    @GetMapping
    @PreAuthorize("hasAnyRole('COMUM', 'ADMIN')")
    public ResponseEntity<List<SpecialityDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                        @RequestParam(value = "linesPerPage", defaultValue = "40") Integer linesPerPage,
                                                        @RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
                                                        @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        Pageable pageSettings = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return ResponseEntity.ok(specService.searchEntityPage(pageSettings).getContent());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('COMUM', 'ADMIN')")
    public ResponseEntity<SpecialityInfoDTO> findById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(specService.findById(id));
    }
}
