package br.com.nivlabs.gp.repository.custom.procedureorevent;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.com.nivlabs.gp.controller.filters.ProcedureOrEventFilters;
import br.com.nivlabs.gp.models.domain.tiss.ProcedureOrEvent;
import br.com.nivlabs.gp.models.dto.ProcedureOrEventDTO;
import br.com.nivlabs.gp.models.enums.ActiveType;
import br.com.nivlabs.gp.repository.custom.CustomFilters;
import br.com.nivlabs.gp.repository.custom.GenericCustomRepository;
import br.com.nivlabs.gp.repository.custom.IExpression;
import br.com.nivlabs.gp.util.StringUtils;

/**
 * Repositório customizado para procedimentos e eventos (TISS + TUSS)
 * 
 * @author viniciosarodrigues
 *
 */
public class ProcedureOrEventRepositoryCustomImpl extends GenericCustomRepository<ProcedureOrEvent>
		implements ProcedureOrEventRepositoryCustom {

	@Override
	public Page<ProcedureOrEventDTO> resumedList(CustomFilters filters, Pageable pageSettings) {
		Page<ProcedureOrEvent> pageFromDatabase = pagination(createRestrictions(filters), pageSettings);

		List<ProcedureOrEventDTO> listOfDTO = new ArrayList<>();

		pageFromDatabase.forEach(entity -> {
			ProcedureOrEventDTO dtoConverted = new ProcedureOrEventDTO();
			BeanUtils.copyProperties(entity, dtoConverted);
			listOfDTO.add(dtoConverted);
		});
		return new PageImpl<>(listOfDTO, pageSettings, pageFromDatabase.getTotalElements());
	}

	@Override
	protected List<IExpression<ProcedureOrEvent>> createRestrictions(CustomFilters customFilters) {
		ProcedureOrEventFilters filters = (ProcedureOrEventFilters) customFilters;

		List<IExpression<ProcedureOrEvent>> attributes = new ArrayList<>();

		if (!StringUtils.isNullOrEmpty(filters.getId()) && StringUtils.isNumeric(filters.getId())) {
			attributes.add((cb, from) -> cb.equal(from.get("id"), Long.parseLong(filters.getId())));
		}
		if (!StringUtils.isNullOrEmpty(filters.getDescription())) {
			attributes.add((cb, from) -> cb.equal(from.get("description"), filters.getDescription()));
		}
		if (filters.getActiveType() != null) {
			attributes.add((cb, from) -> cb.equal(from.get("active"), filters.getActiveType() == ActiveType.ACTIVE));
		}
		return attributes;
	}

}
