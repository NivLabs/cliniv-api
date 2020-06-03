package br.com.nivlabs.gp.controller.attendance;

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

import br.com.nivlabs.gp.event.CreatedResourceEvent;
import br.com.nivlabs.gp.models.domain.AttendanceEvent;
import br.com.nivlabs.gp.service.AttendanceEventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Classe AttendanceEventController.java
 * 
 * @author <a href="mailto:williamsgomes45@gmail.com">Williams Gomes</a>
 *
 * @since 17 Sept, 2019
 */
@Api("Endpoint - Eventos do atendimento")
@RestController
@RequestMapping(value = "/attendance/event")
public class AttendanceEventController {

	@Autowired
	private AttendanceEventService service;

	@Autowired
	private ApplicationEventPublisher publisher;

	@ApiOperation(nickname = "attendance-event-get", value = "Busca uma página de eventos de atendimento")
	@GetMapping
	@PreAuthorize("hasAnyRole('COMUM', 'ADMIN')")
	public ResponseEntity<Page<AttendanceEvent>> findPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Pageable pageSettings = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return ResponseEntity.ok(service.searchEntityPage(pageSettings));
	}

	@ApiOperation(nickname = "attendance-event-post", value = "Insere um novo evento de atendimento")
	@PostMapping
	@PreAuthorize("hasAnyRole('RECEPCAO', 'MEDICO', 'ENFERMEIRO', 'ADMIN')")
	public ResponseEntity<AttendanceEvent> persist(
			@Validated @RequestBody(required = true) AttendanceEvent newAttendanceEvent, HttpServletResponse response) {
		AttendanceEvent createdAttendanceEvent = service.persist(newAttendanceEvent);

		publisher.publishEvent(new CreatedResourceEvent(this, response, createdAttendanceEvent.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(createdAttendanceEvent);

	}

	@ApiOperation(nickname = "attendance-event-put", value = "Atualiza um evento de atendimento na aplicação")
	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('RECEPCAO', 'MEDICO', 'ENFERMEIRO', 'ADMIN')")
	public ResponseEntity<AttendanceEvent> update(@PathVariable("id") Long id,
			@Validated @RequestBody(required = true) AttendanceEvent visitEvent, HttpServletResponse response) {
		AttendanceEvent createdVisitEvent = service.update(id, visitEvent);

		return ResponseEntity.ok().body(createdVisitEvent);

	}

	@ApiOperation(nickname = "attendance-event-get-id", value = "Busca um evento de atendimento baseado no identificador")
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('RECEPCAO', 'MEDICO', 'ENFERMEIRO', 'ADMIN')")
	public ResponseEntity<AttendanceEvent> findById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(service.findById(id));
	}

}
