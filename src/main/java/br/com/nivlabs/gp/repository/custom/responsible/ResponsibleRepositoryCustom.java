package br.com.nivlabs.gp.repository.custom.responsible;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.nivlabs.gp.models.dto.ResponsibleDTO;
import br.com.nivlabs.gp.repository.custom.CustomFilters;

public interface ResponsibleRepositoryCustom {
    public Page<ResponsibleDTO> resumedList(CustomFilters filters, Pageable pageSettings);
}
