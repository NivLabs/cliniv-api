package br.com.ft.gdp.repository.custom.responsible;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.com.ft.gdp.controller.filters.ResponsibleFilters;
import br.com.ft.gdp.models.domain.Responsible;
import br.com.ft.gdp.models.dto.ResponsibleDTO;
import br.com.ft.gdp.repository.custom.CustomFilters;
import br.com.ft.gdp.repository.custom.GenericCustomRepository;
import br.com.ft.gdp.repository.custom.IExpression;
import br.com.ft.gdp.util.StringUtils;

/**
 * Repositório de Profissionais e responsáveis customizado
 * 
 * @author viniciosarodrigues
 *
 */
public class ResponsibleRepositoryCustomImpl extends GenericCustomRepository<Responsible> implements ResponsibleRepositoryCustom {

    @Override
    public Page<ResponsibleDTO> resumedList(CustomFilters filters, Pageable pageSettings) {
        Page<Responsible> pageFromDatabase = pagination(createRestrictions(filters), pageSettings);

        List<ResponsibleDTO> listOfResponsibleDTO = new ArrayList<>();

        pageFromDatabase.forEach(responsible -> {
            ResponsibleDTO responsibleConverted = new ResponsibleDTO();
            BeanUtils.copyProperties(responsible.getPerson(), responsibleConverted, "id");
            BeanUtils.copyProperties(responsible, responsibleConverted);
            listOfResponsibleDTO.add(responsibleConverted);
        });
        return new PageImpl<>(listOfResponsibleDTO, pageSettings, pageFromDatabase.getTotalElements());
    }

    @Override
    protected List<IExpression<Responsible>> createRestrictions(CustomFilters customFilters) {
        ResponsibleFilters filters = (ResponsibleFilters) customFilters;

        List<IExpression<Responsible>> attributes = new ArrayList<>();

        if (!StringUtils.isNullOrEmpty(filters.getId()) && StringUtils.isNoFloatNumeric(filters.getId())) {
            attributes.add((cb, from) -> cb.equal(from.get("id"), Long.parseLong(filters.getId())));
        }
        if (!StringUtils.isNullOrEmpty(filters.getProfessionalIdentity())) {
            attributes.add((cb, from) -> cb.equal(from.get("professionalIdentity"), filters.getProfessionalIdentity()));
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

        return attributes;
    }

}
