package br.com.nivlabs.gp.repository.custom.patient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.nivlabs.gp.models.dto.PatientDTO;
import br.com.nivlabs.gp.repository.custom.CustomFilters;

public interface PatientRepositoryCustom {
    public Page<PatientDTO> resumedList(CustomFilters filters, Pageable pageSettings);
}
