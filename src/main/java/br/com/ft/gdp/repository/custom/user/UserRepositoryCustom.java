package br.com.ft.gdp.repository.custom.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.ft.gdp.controller.filters.UserFilters;
import br.com.ft.gdp.models.dto.UserDTO;

/**
 * Cria uma interface para um repositório customizado de Usuários
 * 
 * @author viniciosarodrigues
 *
 */
public interface UserRepositoryCustom {

    /**
     * Busca uma lista paginada de Usuários
     * 
     * @param filters
     * @param pageSettings
     * @return
     */
    public Page<UserDTO> resumedList(UserFilters filters, Pageable pageSettings);

}
