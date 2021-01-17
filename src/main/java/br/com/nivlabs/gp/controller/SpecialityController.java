package br.com.nivlabs.gp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nivlabs.gp.controller.filters.SpecialityFilter;
import br.com.nivlabs.gp.models.dto.SpecialityDTO;
import br.com.nivlabs.gp.models.dto.SpecialityInfoDTO;
import br.com.nivlabs.gp.service.SpecialityService;
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
    public ResponseEntity<List<SpecialityDTO>> findPage(SpecialityFilter filters) {
        Pageable pageSettings = PageRequest.of(filters.getPage(), filters.getSize(), Direction.valueOf(filters.getDirection()),
                                               filters.getOrderBy());
        return ResponseEntity.ok(specService.searchEntityPage(pageSettings).getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecialityInfoDTO> findById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(specService.findById(id));
    }
}
