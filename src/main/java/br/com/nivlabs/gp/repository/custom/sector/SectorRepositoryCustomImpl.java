package br.com.nivlabs.gp.repository.custom.sector;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.com.nivlabs.gp.controller.filters.SectorFilters;
import br.com.nivlabs.gp.models.domain.Sector;
import br.com.nivlabs.gp.models.dto.SectorDTO;
import br.com.nivlabs.gp.repository.custom.CustomFilters;
import br.com.nivlabs.gp.repository.custom.GenericCustomRepository;
import br.com.nivlabs.gp.repository.custom.IExpression;
import br.com.nivlabs.gp.util.StringUtils;

/**
 * Repositório de Profissionais e responsáveis customizado
 * 
 * @author viniciosarodrigues
 *
 */
public class SectorRepositoryCustomImpl extends GenericCustomRepository<Sector> implements SectorRepositoryCustom {

	@Override
	public Page<SectorDTO> resumedList(CustomFilters filters, Pageable pageSettings) {
		Page<Sector> pageFromDatabase = pagination(createRestrictions(filters), pageSettings);

		List<SectorDTO> listOfSectorDTO = new ArrayList<>();

		pageFromDatabase.forEach(sector -> {
			SectorDTO sectorConverted = new SectorDTO();
			BeanUtils.copyProperties(sector, sectorConverted);
			listOfSectorDTO.add(sectorConverted);
		});
		return new PageImpl<>(listOfSectorDTO, pageSettings, pageFromDatabase.getTotalElements());
	}

	@Override
	protected List<IExpression<Sector>> createRestrictions(CustomFilters customFilters) {
		SectorFilters filters = (SectorFilters) customFilters;

		List<IExpression<Sector>> attributes = new ArrayList<>();

		if (!StringUtils.isNullOrEmpty(filters.getId()) && StringUtils.isNumeric(filters.getId())) {
			attributes.add((cb, from) -> cb.equal(from.get("id"), Long.parseLong(filters.getId())));
		}
		if (!StringUtils.isNullOrEmpty(filters.getDescription())) {
			attributes.add((cb, from) -> cb.like(from.get("description"), filters.getDescription()));
		}

		return attributes;
	}

}
