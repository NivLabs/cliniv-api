package br.com.nivlabs.gp.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.AttendanceEvent;
import br.com.nivlabs.gp.repository.AttendanceEventRepository;

/**
 * Classe VisitEventService.java
 * 
 * @author <a href="mailto:williamsgomes45@gmail.com">Williams Gomes</a>
 *
 * @since 17 Sept, 2019
 */
@Service
public class AttendanceEventService implements GenericService<AttendanceEvent, Long> {

	@Autowired
	private AttendanceEventRepository dao;

	@Override
	public Page<AttendanceEvent> searchEntityPage(Pageable pageRequest) {
		return dao.findAll(pageRequest);
	}

	@Override
	public AttendanceEvent findById(Long id) {
		return dao.findById(id).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
				String.format("Evento de Visita com o ID: [%s] não encontrado", id)));
	}

	@Override
	public AttendanceEvent update(Long id, AttendanceEvent entity) {
		AttendanceEvent auxEntity = findById(id);
		BeanUtils.copyProperties(entity, auxEntity, "id");
		return dao.save(auxEntity);
	}

	@Override
	public void delete(AttendanceEvent entity) {
		deleteById(entity.getId());
	}

	@Override
	public void deleteById(Long id) {
		AttendanceEvent auxEntity = findById(id);
		dao.delete(auxEntity);
	}

	@Override
	public AttendanceEvent persist(AttendanceEvent entity) {
		return dao.save(entity);
	}

	/**
	 * @param visitId
	 * @return
	 */
	public List<AttendanceEvent> findByVisitId(Long visitId) {
		return dao.findByAttendanceId(visitId);
	}

}
