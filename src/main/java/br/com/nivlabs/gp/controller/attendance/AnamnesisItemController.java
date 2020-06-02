
/**
 * 
 */
package br.com.nivlabs.gp.controller.attendance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nivlabs.gp.models.dto.AnamnesisItemDTO;
import br.com.nivlabs.gp.repository.custom.CustomFilters;
import br.com.nivlabs.gp.service.AnamnesisItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * AnamneseController.java
 *
 * @author <a href="henriquedreyer@gmail.com">Henrique Dreyer</a>
 *
 * @since 14 de set de 2019
 * 
 */

@Api(value = "Endpoint - Itens da Anamnese")
@RestController
@RequestMapping("/attendance/anamnese-item")
public class AnamnesisItemController {

	@Autowired
	private AnamnesisItemService service;

	@ApiOperation(nickname = "anamnese-item-get", value = "Busca uma página de Questões de Anamnese")
	@GetMapping
	@PreAuthorize("hasAnyRole('ATENDIMENTO_ESCRITA', 'ATENDIMENTO_LEITURA', 'ADMIN')")
	public ResponseEntity<Page<AnamnesisItemDTO>> findPage(CustomFilters filters) {
		Pageable pageSettings = PageRequest.of(filters.getPage(), filters.getSize(),
				Direction.valueOf(filters.getDirection()), filters.getOrderBy());
		return ResponseEntity.ok(service.searchDTOPage(pageSettings));
	}
}
