package br.com.tl.gdp.repository.custom.attendance;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.tl.gdp.models.dto.AttendanceDTO;
import br.com.tl.gdp.repository.custom.CustomFilters;

public interface AttendanceRepositoryCustom {
    public Page<AttendanceDTO> resumedList(CustomFilters filters, Pageable pageSettings);
}
