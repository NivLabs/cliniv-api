package br.com.tl.gdp.repository.custom.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.tl.gdp.models.dto.UserDTO;
import br.com.tl.gdp.repository.custom.CustomFilters;

public interface UserRepositoryCustom {
    public Page<UserDTO> resumedList(CustomFilters filters, Pageable pageSettings);
}
