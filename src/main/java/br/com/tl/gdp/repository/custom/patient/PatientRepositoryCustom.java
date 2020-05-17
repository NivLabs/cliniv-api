package br.com.tl.gdp.repository.custom.patient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.tl.gdp.models.dto.PatientDTO;
import br.com.tl.gdp.repository.custom.CustomFilters;

public interface PatientRepositoryCustom {
    public Page<PatientDTO> resumedList(CustomFilters filters, Pageable pageSettings);
}
