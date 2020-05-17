package br.com.tl.gdp.repository.custom.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.com.tl.gdp.controller.filters.UserFilters;
import br.com.tl.gdp.models.domain.UserApplication;
import br.com.tl.gdp.models.dto.UserDTO;
import br.com.tl.gdp.repository.custom.CustomFilters;
import br.com.tl.gdp.repository.custom.GenericCustomRepository;
import br.com.tl.gdp.repository.custom.IExpression;
import br.com.tl.gdp.util.StringUtils;

/**
 * Repositório de usuário customizado
 * 
 * @author viniciosarodrigues
 *
 */
public class UserRepositoryCustomImpl extends GenericCustomRepository<UserApplication> implements UserRepositoryCustom {

    /**
     * Realiza o filtro por
     */
    @Override
    public Page<UserDTO> resumedList(CustomFilters filters, Pageable pageSettings) {
        Page<UserApplication> pageFromDatabase = pagination(createRestrictions(filters), pageSettings);

        List<UserDTO> newContentToPage = new ArrayList<>();

        pageFromDatabase.getContent().forEach(item -> {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(item.getPerson(), userDTO);
            BeanUtils.copyProperties(item, userDTO);
            newContentToPage.add(userDTO);
        });

        return new PageImpl<>(newContentToPage, pageSettings, pageFromDatabase.getTotalElements());
    }

    @Override
    protected List<IExpression<UserApplication>> createRestrictions(CustomFilters customFilters) {

        UserFilters filters = (UserFilters) customFilters;

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
