package br.com.nivlabs.gp.repository.custom.cbo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import br.com.nivlabs.gp.controller.filters.CBOFilters;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.CBOTable;
import br.com.nivlabs.gp.models.domain.CBOTable_;
import br.com.nivlabs.gp.models.dto.CBOTableDTO;
import br.com.nivlabs.gp.repository.custom.CustomFilters;
import br.com.nivlabs.gp.repository.custom.GenericCustomRepository;
import br.com.nivlabs.gp.util.StringUtils;

/**
 * Repositório customisado para CBO
 * 
 * @author viniciosarodrigues
 *
 */
public class CBOTableRepositoryCustomImpl extends GenericCustomRepository<CBOTable, CBOTableDTO> implements CBOTableRepositoryCustom {

    @Override
    public Page<CBOTableDTO> resumedList(CustomFilters filters, Pageable pageSettings) {

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<CBOTableDTO> criteria = builder.createQuery(resumedClass);
        Root<CBOTable> root = criteria.from(persistentClass);

        criteria.select(builder.construct(resumedClass,
                                          root.get(CBOTable_.id),
                                          root.get(CBOTable_.description)));
        return getPage(filters, pageSettings, builder, criteria, root);
    }

    @Override
    protected Predicate[] createRestrictions(CustomFilters customFilters, CriteriaBuilder builder, Root<CBOTable> root) {
        if (!(customFilters instanceof CBOFilters)) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "O filtro enviado não é um filtro de CBO");
        }
        CBOFilters filters = (CBOFilters) customFilters;
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isNullOrEmpty(filters.getId()) && StringUtils.isNumeric(filters.getId())) {
            predicates.add(builder.equal(root.get(CBOTable_.id), Long.parseLong(filters.getId())));
        }
        if (!StringUtils.isNullOrEmpty(filters.getDescription())) {
            predicates.add(builder.like(root.get(CBOTable_.description), filters.getDescription()));
        }
        return predicates.toArray(new Predicate[predicates.size()]);
    }

}
