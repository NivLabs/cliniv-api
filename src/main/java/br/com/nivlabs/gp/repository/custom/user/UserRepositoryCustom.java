package br.com.nivlabs.gp.repository.custom.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.nivlabs.gp.models.dto.UserDTO;
import br.com.nivlabs.gp.repository.custom.CustomFilters;

public interface UserRepositoryCustom {
    public Page<UserDTO> resumedList(CustomFilters filters, Pageable pageSettings);
}
