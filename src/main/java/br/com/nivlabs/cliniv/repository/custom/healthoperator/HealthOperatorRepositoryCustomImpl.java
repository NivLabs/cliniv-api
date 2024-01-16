package br.com.nivlabs.cliniv.repository.custom.healthoperator;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import br.com.nivlabs.cliniv.controller.filters.HealthOperatorFilters;
import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.HealthOperator;
import br.com.nivlabs.cliniv.models.domain.HealthOperator_;
import br.com.nivlabs.cliniv.models.dto.HealthOperatorDTO;
import br.com.nivlabs.cliniv.repository.custom.CustomFilters;
import br.com.nivlabs.cliniv.repository.custom.GenericCustomRepository;
import br.com.nivlabs.cliniv.util.StringUtils;

/**
 * Repositório customizado para operadoras de saúde
 *
 * @author viniciosarodrigues
 */
public class HealthOperatorRepositoryCustomImpl extends GenericCustomRepository<HealthOperator, HealthOperatorDTO>
        implements HealthOperatorRepositoryCustom {

    @Override
    public Page<HealthOperatorDTO> resumedList(CustomFilters filters) {

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<HealthOperatorDTO> criteria = builder.createQuery(HealthOperatorDTO.class);
        Root<HealthOperator> root = criteria.from(HealthOperator.class);

        criteria.select(builder.construct(HealthOperatorDTO.class,
                root.get("id"),
                root.get("ansCode"),
                root.get("cnpj"),
                root.get("companyName"),
                root.get("fantasyName"),
                root.get("modality")));
        return getPage(filters, builder, criteria, root);
    }

    @Override
    protected Predicate[] createRestrictions(CustomFilters customFilters, CriteriaBuilder builder, Root<HealthOperator> root) {
        if (!(customFilters instanceof HealthOperatorFilters filters)) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "Tipo de filtro inválido para Operadoras de saúde!");
        }
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isNullOrEmpty(filters.getId()) && StringUtils.isNumeric(filters.getId())) {
            predicates.add(builder.equal(root.get("id"), Long.parseLong(filters.getId())));
        }
        if (!StringUtils.isNullOrEmpty(filters.getAnsCode())) {
            predicates.add(builder.equal(root.get("ansCode"), Long.parseLong(filters.getAnsCode())));
        }
        if (!StringUtils.isNullOrEmpty(filters.getCnpj())) {
            predicates.add(builder.equal(root.get("cnpj"), filters.getCnpj()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getCompanyName())) {
            predicates.add(builder.like(root.get("companyName"), filters.getCompanyName()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getFantasyName())) {
            predicates.add(builder.like(root.get("fantasyName"), filters.getFantasyName()));
        }
        if (filters.getModality() != null) {
            predicates.add(builder.equal(root.get("modality"), filters.getModality()));
        }
        return predicates.toArray(new Predicate[0]);
    }
}
