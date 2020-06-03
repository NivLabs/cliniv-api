package br.com.nivlabs.gp.repository.custom.cbo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.com.nivlabs.gp.controller.filters.CBOFilters;
import br.com.nivlabs.gp.models.domain.CBOTable;
import br.com.nivlabs.gp.models.dto.CBOTableDTO;
import br.com.nivlabs.gp.repository.custom.CustomFilters;
import br.com.nivlabs.gp.repository.custom.GenericCustomRepository;
import br.com.nivlabs.gp.repository.custom.IExpression;
import br.com.nivlabs.gp.util.StringUtils;

/**
 * Repositório customisado para CBO
 * 
 * @author viniciosarodrigues
 *
 */
public class CBOTableRepositoryCustomImpl extends GenericCustomRepository<CBOTable>
		implements CBOTableRepositoryCustom {

	@Override
	public Page<CBOTableDTO> resumedList(CustomFilters filters, Pageable pageSettings) {
		Page<CBOTable> pageFromDatabase = pagination(createRestrictions(filters), pageSettings);

		List<CBOTableDTO> listOfDTO = new ArrayList<>();

		pageFromDatabase.forEach(entity -> {
			CBOTableDTO dtoConverted = new CBOTableDTO();
			BeanUtils.copyProperties(entity, dtoConverted);
			listOfDTO.add(dtoConverted);
		});
		return new PageImpl<>(listOfDTO, pageSettings, pageFromDatabase.getTotalElements());

	}

	@Override
	protected List<IExpression<CBOTable>> createRestrictions(CustomFilters customFilters) {
		CBOFilters filters = (CBOFilters) customFilters;
		List<IExpression<CBOTable>> attributes = new ArrayList<>();

		if (!StringUtils.isNullOrEmpty(filters.getId()) && StringUtils.isNumeric(filters.getId())) {
			attributes.add((cb, from) -> cb.equal(from.get("id"), Long.parseLong(filters.getId())));
		}
		if (!StringUtils.isNullOrEmpty(filters.getDescription())) {
			attributes.add((cb, from) -> cb.like(from.get("description"), filters.getDescription()));
		}
		return attributes;
	}

}
