package br.com.nivlabs.gp.repository.custom.attendance;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.nivlabs.gp.models.dto.AttendanceDTO;
import br.com.nivlabs.gp.repository.custom.CustomFilters;

public interface AttendanceRepositoryCustom {
    public Page<AttendanceDTO> resumedList(CustomFilters filters, Pageable pageSettings);
}
