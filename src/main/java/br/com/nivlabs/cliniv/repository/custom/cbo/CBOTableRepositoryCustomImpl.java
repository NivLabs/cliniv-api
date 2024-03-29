package br.com.nivlabs.cliniv.repository.custom.cbo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import br.com.nivlabs.cliniv.controller.filters.CBOFilters;
import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.CBOTable;
import br.com.nivlabs.cliniv.models.domain.CBOTable_;
import br.com.nivlabs.cliniv.models.dto.CBOTableDTO;
import br.com.nivlabs.cliniv.repository.custom.CustomFilters;
import br.com.nivlabs.cliniv.repository.custom.GenericCustomRepository;
import br.com.nivlabs.cliniv.util.StringUtils;

/**
 * Repositório customisado para CBO
 *
 * @author viniciosarodrigues
 */
public class CBOTableRepositoryCustomImpl extends GenericCustomRepository<CBOTable, CBOTableDTO> implements CBOTableRepositoryCustom {

    @Override
    public Page<CBOTableDTO> resumedList(CustomFilters filters) {

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<CBOTableDTO> criteria = builder.createQuery(CBOTableDTO.class);
        Root<CBOTable> root = criteria.from(CBOTable.class);

        criteria.select(builder.construct(CBOTableDTO.class,
                root.get("id"),
                root.get("description")));
        return getPage(filters, builder, criteria, root);
    }

    @Override
    protected Predicate[] createRestrictions(CustomFilters customFilters, CriteriaBuilder builder, Root<CBOTable> root) {
        if (!(customFilters instanceof CBOFilters filters)) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "O filtro enviado não é um filtro de CBO");
        }

        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isNullOrEmpty(filters.getId()) && StringUtils.isNumeric(filters.getId())) {
            predicates.add(builder.equal(root.get("id"), Long.parseLong(filters.getId())));
        }
        if (!StringUtils.isNullOrEmpty(filters.getDescription())) {
            predicates.add(builder.like(root.get("description"), filters.getDescription()));
        }
        return predicates.toArray(new Predicate[0]);
    }

}
