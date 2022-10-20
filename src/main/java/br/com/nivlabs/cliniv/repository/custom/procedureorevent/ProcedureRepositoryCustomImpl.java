package br.com.nivlabs.cliniv.repository.custom.procedureorevent;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import br.com.nivlabs.cliniv.controller.filters.ProcedureFilters;
import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.tiss.Procedure;
import br.com.nivlabs.cliniv.models.domain.tiss.Procedure_;
import br.com.nivlabs.cliniv.models.dto.ProcedureDTO;
import br.com.nivlabs.cliniv.repository.custom.CustomFilters;
import br.com.nivlabs.cliniv.repository.custom.GenericCustomRepository;
import br.com.nivlabs.cliniv.util.StringUtils;

/**
 * Repositório customizado para procedimentos e eventos (TISS + TUSS)
 * 
 * @author viniciosarodrigues
 *
 */
public class ProcedureRepositoryCustomImpl extends GenericCustomRepository<Procedure, ProcedureDTO>
        implements ProcedureRepositoryCustom {

    @Override
    public Page<ProcedureDTO> resumedList(CustomFilters filters) {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<ProcedureDTO> criteria = builder.createQuery(ProcedureDTO.class);
        Root<Procedure> root = criteria.from(Procedure.class);

        criteria.select(builder.construct(ProcedureDTO.class,
                                          root.get(Procedure_.id),
                                          root.get(Procedure_.description),
                                          root.get(Procedure_.baseValue),
                                          root.get(Procedure_.frequency),
                                          root.get(Procedure_.specialAuthorization)));
        return getPage(filters, builder, criteria, root);
    }

    @Override
    protected Predicate[] createRestrictions(CustomFilters customFilters, CriteriaBuilder builder, Root<Procedure> root) {
        if (!(customFilters instanceof ProcedureFilters filters)) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "Tipo de filtro inválido para Procedimentos!");
        }
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isNullOrEmpty(filters.getId()) && StringUtils.isNumeric(filters.getId())) {
            predicates.add(builder.equal(root.get(Procedure_.id), Long.parseLong(filters.getId())));
        }
        if (!StringUtils.isNullOrEmpty(filters.getDescription())) {
            predicates.add(builder.like(root.get(Procedure_.description), filters.getDescription()));
        }
        return predicates.toArray(new Predicate[predicates.size()]);
    }

}
