package br.com.nivlabs.gp.repository.custom.sector;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.nivlabs.gp.models.dto.SectorDTO;
import br.com.nivlabs.gp.repository.custom.CustomFilters;

public interface SectorRepositoryCustom {
	public Page<SectorDTO> resumedList(CustomFilters filters, Pageable pageSettings);
}
