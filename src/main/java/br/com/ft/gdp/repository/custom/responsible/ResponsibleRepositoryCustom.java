package br.com.ft.gdp.repository.custom.responsible;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.ft.gdp.models.dto.ResponsibleDTO;
import br.com.ft.gdp.repository.custom.CustomFilters;

public interface ResponsibleRepositoryCustom {
    public Page<ResponsibleDTO> resumedList(CustomFilters filters, Pageable pageSettings);
}
