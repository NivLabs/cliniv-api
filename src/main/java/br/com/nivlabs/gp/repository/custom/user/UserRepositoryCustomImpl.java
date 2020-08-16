package br.com.nivlabs.gp.repository.custom.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.com.nivlabs.gp.controller.filters.UserFilters;
import br.com.nivlabs.gp.models.domain.Person_;
import br.com.nivlabs.gp.models.domain.UserApplication;
import br.com.nivlabs.gp.models.domain.UserApplication_;
import br.com.nivlabs.gp.models.dto.UserDTO;
import br.com.nivlabs.gp.repository.custom.CustomFilters;
import br.com.nivlabs.gp.repository.custom.GenericCustomRepository;
import br.com.nivlabs.gp.repository.custom.IExpression;
import br.com.nivlabs.gp.util.StringUtils;

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
            attributes.add((cb, from) -> cb.like(from.get(UserApplication_.userName), filters.getUserName()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getCpf())) {
            attributes.add((cb, from) -> cb.equal(from.get(UserApplication_.person).get(Person_.cpf), filters.getCpf()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getFullName())) {
            attributes.add((cb, from) -> cb.like(from.get(UserApplication_.person).get(Person_.fullName), filters.getFullName()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getSocialName())) {
            attributes.add((cb, from) -> cb.like(from.get(UserApplication_.person).get(Person_.socialName), filters.getSocialName()));
        }
        if (filters.getGender() != null) {
            attributes.add((cb, from) -> cb.equal(from.get(UserApplication_.person).get(Person_.gender), filters.getGender()));
        }

        return attributes;
    }
}
