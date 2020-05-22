/**
 * 
 */
package br.com.nivlabs.gp.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.controller.filters.ProcedureOrEventFilters;
import br.com.nivlabs.gp.exception.ObjectNotFoundException;
import br.com.nivlabs.gp.models.domain.tiss.ProcedureOrEvent;
import br.com.nivlabs.gp.models.dto.ProcedureOrEventDTO;
import br.com.nivlabs.gp.repository.ProcedureOrEventRepository;

/**
 * Camada de serviço para procedimentos e eventos clínicos/hospitalares
 * 
 * @author viniciosarodrigues
 *
 */
@Service
public class ProcedureOrEventService implements GenericService<ProcedureOrEvent, Long> {

	@Autowired
	private ProcedureOrEventRepository dao;

	public Page<ProcedureOrEventDTO> getResumedPage(ProcedureOrEventFilters filters, Pageable pageRequest) {
		return dao.resumedList(filters, pageRequest);
	}

	@Override
	public ProcedureOrEvent findById(Long id) {
		return dao.findById(id).orElseThrow(
				() -> new ObjectNotFoundException(String.format("Procedimento com código %s não encontrado!", id)));
	}

	@Override
	public ProcedureOrEvent update(Long id, ProcedureOrEvent entity) {
		ProcedureOrEvent procedureOrEvent = findById(id);
		BeanUtils.copyProperties(entity, procedureOrEvent, "id");
		return dao.save(procedureOrEvent);
	}

	@Override
	public void delete(ProcedureOrEvent entity) {
		deleteById(entity.getId());
	}

	@Override
	public void deleteById(Long id) {
		ProcedureOrEvent procedureOrEvent = findById(id);
		dao.delete(procedureOrEvent);
	}

	@Override
	public ProcedureOrEvent persist(ProcedureOrEvent entity) {
		entity.setId(null);
		return dao.save(entity);
	}

	public void enableDisable(Long id) {
		ProcedureOrEvent procedure = findById(id);
		procedure.setActive(!procedure.isActive());
		update(id, procedure);
	}

}
