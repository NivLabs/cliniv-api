package br.com.nivlabs.gp.repository.custom.procedureorevent;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.com.nivlabs.gp.controller.filters.ProcedureFilters;
import br.com.nivlabs.gp.models.domain.tiss.Procedure;
import br.com.nivlabs.gp.models.domain.tiss.Procedure_;
import br.com.nivlabs.gp.models.dto.ProcedureDTO;
import br.com.nivlabs.gp.models.enums.ActiveType;
import br.com.nivlabs.gp.repository.custom.CustomFilters;
import br.com.nivlabs.gp.repository.custom.GenericCustomRepository;
import br.com.nivlabs.gp.repository.custom.IExpression;
import br.com.nivlabs.gp.util.StringUtils;

/**
 * Repositório customizado para procedimentos e eventos (TISS + TUSS)
 * 
 * @author viniciosarodrigues
 *
 */
public class ProcedureRepositoryCustomImpl extends GenericCustomRepository<Procedure>
        implements ProcedureRepositoryCustom {

    @Override
    public Page<ProcedureDTO> resumedList(CustomFilters filters, Pageable pageSettings) {
        Page<Procedure> pageFromDatabase = pagination(createRestrictions(filters), pageSettings);

        List<ProcedureDTO> listOfDTO = new ArrayList<>();

        pageFromDatabase.forEach(entity -> {
            ProcedureDTO dtoConverted = new ProcedureDTO();
            BeanUtils.copyProperties(entity, dtoConverted);
            listOfDTO.add(dtoConverted);
        });
        return new PageImpl<>(listOfDTO, pageSettings, pageFromDatabase.getTotalElements());
    }

    @Override
    protected List<IExpression<Procedure>> createRestrictions(CustomFilters customFilters) {
        ProcedureFilters filters = (ProcedureFilters) customFilters;

        List<IExpression<Procedure>> attributes = new ArrayList<>();

        if (!StringUtils.isNullOrEmpty(filters.getId()) && StringUtils.isNumeric(filters.getId())) {
            attributes.add((cb, from) -> cb.equal(from.get(Procedure_.id), Long.parseLong(filters.getId())));
        }
        if (!StringUtils.isNullOrEmpty(filters.getDescription())) {
            attributes.add((cb, from) -> cb.like(from.get(Procedure_.description), filters.getDescription()));
        }
        if (filters.getActiveType() != null) {
            attributes.add((cb, from) -> cb.equal(from.get(Procedure_.active),
                                                  filters.getActiveType() == ActiveType.ACTIVE));
        }
        return attributes;
    }

}
