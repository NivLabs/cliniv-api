package br.com.ft.gdp.repository.custom.attendance;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.ft.gdp.models.dto.AttendanceDTO;
import br.com.ft.gdp.repository.custom.CustomFilters;

public interface AttendanceRepositoryCustom {
    public Page<AttendanceDTO> resumedList(CustomFilters filters, Pageable pageSettings);
}
