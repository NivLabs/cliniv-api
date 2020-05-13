package br.com.ft.gdp.repository.custom.patient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.ft.gdp.models.dto.PatientDTO;
import br.com.ft.gdp.repository.custom.CustomFilters;

public interface PatientRepositoryCustom {
    public Page<PatientDTO> resumedList(CustomFilters filters, Pageable pageSettings);
}
