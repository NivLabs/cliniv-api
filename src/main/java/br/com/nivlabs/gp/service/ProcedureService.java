/**
 * 
 */
package br.com.nivlabs.gp.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.controller.filters.ProcedureFilters;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.tiss.Procedure;
import br.com.nivlabs.gp.models.dto.ProcedureDTO;
import br.com.nivlabs.gp.repository.ProcedureRepository;

/**
 * Camada de serviço para procedimentos e eventos clínicos/hospitalares
 * 
 * @author viniciosarodrigues
 *
 */
@Service
public class ProcedureService implements GenericService<Procedure, Long> {

	@Autowired
	private ProcedureRepository dao;

	public Page<ProcedureDTO> getResumedPage(ProcedureFilters filters, Pageable pageRequest) {
		return dao.resumedList(filters, pageRequest);
	}

	@Override
	public Procedure findById(Long id) {
		return dao.findById(id).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
				String.format("Procedimento com código %s não encontrado!", id)));
	}

	@Override
	public Procedure update(Long id, Procedure entity) {
		Procedure procedureOrEvent = findById(id);
		BeanUtils.copyProperties(entity, procedureOrEvent, "id");
		return dao.save(procedureOrEvent);
	}

	@Override
	public void delete(Procedure entity) {
		deleteById(entity.getId());
	}

	@Override
	public void deleteById(Long id) {
		Procedure procedureOrEvent = findById(id);
		dao.delete(procedureOrEvent);
	}

	@Override
	public Procedure persist(Procedure entity) {
		entity.setId(null);
		return dao.save(entity);
	}

	public void enableDisable(Long id) {
		Procedure procedure = findById(id);
		procedure.setActive(!procedure.isActive());
		update(id, procedure);
	}

}
