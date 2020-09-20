package br.com.nivlabs.gp.repository.custom.responsible;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.com.nivlabs.gp.controller.filters.ResponsibleFilters;
import br.com.nivlabs.gp.models.domain.Person_;
import br.com.nivlabs.gp.models.domain.Responsible;
import br.com.nivlabs.gp.models.domain.Responsible_;
import br.com.nivlabs.gp.models.dto.ResponsibleDTO;
import br.com.nivlabs.gp.repository.custom.CustomFilters;
import br.com.nivlabs.gp.repository.custom.GenericCustomRepository;
import br.com.nivlabs.gp.repository.custom.IExpression;
import br.com.nivlabs.gp.util.StringUtils;

/**
 * Repositório de Profissionais e responsáveis customizado
 * 
 * @author viniciosarodrigues
 *
 */
public class ResponsibleRepositoryCustomImpl extends GenericCustomRepository<Responsible>
        implements ResponsibleRepositoryCustom {

    @Override
    public Page<ResponsibleDTO> resumedList(CustomFilters filters, Pageable pageSettings) {
        Page<Responsible> pageFromDatabase = pagination(createRestrictions(filters), pageSettings);

        List<ResponsibleDTO> listOfResponsibleDTO = new ArrayList<>();

        pageFromDatabase.forEach(responsible -> {
            ResponsibleDTO responsibleConverted = new ResponsibleDTO();
            BeanUtils.copyProperties(responsible.getPerson(), responsibleConverted, Person_.ID);
            BeanUtils.copyProperties(responsible, responsibleConverted);
            listOfResponsibleDTO.add(responsibleConverted);
        });
        return new PageImpl<>(listOfResponsibleDTO, pageSettings, pageFromDatabase.getTotalElements());
    }

    @Override
    protected List<IExpression<Responsible>> createRestrictions(CustomFilters customFilters) {
        ResponsibleFilters filters = (ResponsibleFilters) customFilters;

        List<IExpression<Responsible>> attributes = new ArrayList<>();

        if (!StringUtils.isNullOrEmpty(filters.getId()) && StringUtils.isNumeric(filters.getId())) {
            attributes.add((cb, from) -> cb.equal(from.get(Responsible_.id), Long.parseLong(filters.getId())));
        }
        if (!StringUtils.isNullOrEmpty(filters.getProfessionalIdentity())) {
            attributes.add((cb, from) -> cb.equal(from.get(Responsible_.professionalIdentity),
                                                  filters.getProfessionalIdentity()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getCpf())) {
            attributes.add((cb, from) -> cb.equal(from.get(Responsible_.person).get(Person_.cpf), filters.getCpf()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getFullName())) {
            attributes.add((cb, from) -> cb.like(from.get(Responsible_.person).get(Person_.fullName),
                                                 filters.getFullName()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getSocialName())) {
            attributes.add(
                           (cb, from) -> cb.like(from.get(Responsible_.person).get(Person_.socialName), filters.getSocialName()));
        }

        return attributes;
    }

}
