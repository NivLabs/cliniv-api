/**
 * 
 */
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
import br.com.ft.gdp.models.domain.AnamnesisItem;
import br.com.ft.gdp.models.dto.AnamnesisItemDTO;
import br.com.ft.gdp.service.AnamnesisItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * AnamnesisItemController.java
 *
 * @author <a href="henriquedreyer@gmail.com">Henrique Dreyer</a>
 *
 * @since 14 de set de 2019
 * 
 */

@Api("Endpoint - AnamnesisItem")
@RestController
@RequestMapping("/AnamnesisItem")
public class AnamnesisItemController {
	
	@Autowired
	private AnamnesisItemService service;

	@Autowired
	private ApplicationEventPublisher publisher;

	@ApiOperation(nickname = "AnamnesisItem-get", value = "Busca uma página de Itens Anamnesis")
	@GetMapping
	public ResponseEntity<Page<AnamnesisItem>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Pageable pageSettings = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return ResponseEntity.ok(service.searchEntityPage(pageSettings));
	}

	@ApiOperation(nickname = "AnamnesisItem-post", value = "Insere uma nova anamnese na aplicação")
	@PostMapping
	public ResponseEntity<AnamnesisItemDTO> persist(@Validated @RequestBody(required = true) AnamnesisItemDTO anamnesisItemDTO,
			HttpServletResponse response) {
		AnamnesisItem createdAnamnesisItem = service.persist(anamnesisItemDTO.getAnamnesisItemDomainFromDTO());

		publisher.publishEvent(new CreatedResourceEvent(this, response, createdAnamnesisItem.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(createdAnamnesisItem.getAnamnesisItemDTOFromDomain());

	}

	@ApiOperation(nickname = "AnamnesisItem-put", value = "Atualiza uma anamnese na aplicação")
	@PutMapping("/{id}")
	public ResponseEntity<AnamnesisItemDTO> update(@PathVariable("id") Long id,
			@Validated @RequestBody(required = true) AnamnesisItemDTO anamnesisItemDTO, HttpServletResponse response) {
		AnamnesisItem createdAnamnese = service.update(id, anamnesisItemDTO.getAnamnesisItemDomainFromDTO());

		return ResponseEntity.ok().body(createdAnamnese.getAnamnesisItemDTOFromDomain());

	}

	@ApiOperation(nickname = "AnamnesisItem-get-id", value = "Busca uma anamnese baseada no identificador")
	@GetMapping("/{id}")
	public ResponseEntity<AnamnesisItem> findById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(service.findById(id));
	}
}
