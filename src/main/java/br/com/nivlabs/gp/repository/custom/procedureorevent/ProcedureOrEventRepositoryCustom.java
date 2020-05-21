package br.com.nivlabs.gp.repository.custom.procedureorevent;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.nivlabs.gp.models.dto.ProcedureOrEventDTO;
import br.com.nivlabs.gp.repository.custom.CustomFilters;

public interface ProcedureOrEventRepositoryCustom {
    public Page<ProcedureOrEventDTO> resumedList(CustomFilters filters, Pageable pageSettings);
}
