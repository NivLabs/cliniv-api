package br.com.ft.gdp.repository.custom.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.com.ft.gdp.controller.filters.UserFilters;
import br.com.ft.gdp.models.domain.UserApplication;
import br.com.ft.gdp.models.dto.UserDTO;
import br.com.ft.gdp.repository.custom.GenericCustomRepository;
import br.com.ft.gdp.repository.custom.IExpression;
import br.com.ft.gdp.util.StringUtils;

public class UserRepositoryCustomImpl extends GenericCustomRepository<UserApplication> implements UserRepositoryCustom {

    /**
     * Realiza o filtro por
     */
    @Override
    public Page<UserDTO> resumedList(UserFilters filters, Pageable pageSettings) {
        Page<UserApplication> pageFromDatabase = pagination(createRestrictions(filters), pageSettings);

        List<UserDTO> newContentToPage = new ArrayList<>();

        pageFromDatabase.getContent().forEach(item -> {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(item.getPerson(), userDTO);
            BeanUtils.copyProperties(item, userDTO);
            newContentToPage.add(userDTO);
        });

        return new PageImpl<>(newContentToPage, pageSettings, newContentToPage.size());
    }

    /**
     * Cria restrições baseadas nos filtros
     * 
     * @param filters
     * @return
     */
    private List<IExpression<UserApplication>> createRestrictions(UserFilters filters) {
        List<IExpression<UserApplication>> attributes = new ArrayList<>();

        if (!StringUtils.isNullOrEmpty(filters.getUserName())) {
            attributes.add((cb, from) -> cb.like(from.get("userName"), filters.getUserName()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getCpf())) {
            attributes.add((cb, from) -> cb.equal(from.get("person").get("cpf"), filters.getCpf()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getFirstName())) {
            attributes.add((cb, from) -> cb.like(from.get("person").get("firstName"), filters.getFirstName()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getLastName())) {
            attributes.add((cb, from) -> cb.like(from.get("person").get("lastName"), filters.getLastName()));
        }
        if (filters.getGender() != null) {
            attributes.add((cb, from) -> cb.equal(from.get("person").get("gender"), filters.getGender()));
        }

        return attributes;
    }
}
