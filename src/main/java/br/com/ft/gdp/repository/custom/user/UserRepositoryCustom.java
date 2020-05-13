package br.com.ft.gdp.repository.custom.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.ft.gdp.models.dto.UserDTO;
import br.com.ft.gdp.repository.custom.CustomFilters;

public interface UserRepositoryCustom {
    public Page<UserDTO> resumedList(CustomFilters filters, Pageable pageSettings);
}
