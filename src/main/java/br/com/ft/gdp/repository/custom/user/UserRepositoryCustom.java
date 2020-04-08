package br.com.ft.gdp.repository.custom.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.ft.gdp.controller.filters.UserFilters;
import br.com.ft.gdp.models.dto.UserDTO;

public interface UserRepositoryCustom {

    public Page<UserDTO> resumedList(UserFilters filters, Pageable pageSettings);

}
