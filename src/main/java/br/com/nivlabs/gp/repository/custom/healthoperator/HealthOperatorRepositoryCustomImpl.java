package br.com.nivlabs.gp.repository.custom.healthoperator;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import br.com.nivlabs.gp.controller.filters.HealthOperatorFilters;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.HealthOperator;
import br.com.nivlabs.gp.models.domain.HealthOperator_;
import br.com.nivlabs.gp.models.dto.HealthOperatorDTO;
import br.com.nivlabs.gp.repository.custom.CustomFilters;
import br.com.nivlabs.gp.repository.custom.GenericCustomRepository;
import br.com.nivlabs.gp.util.StringUtils;

/**
 * Repositório customizado para operadoras de saúde
 * 
 * @author viniciosarodrigues
 *
 */
public class HealthOperatorRepositoryCustomImpl extends GenericCustomRepository<HealthOperator, HealthOperatorDTO>
        implements HealthOperatorRepositoryCustom {

    @Override
    public Page<HealthOperatorDTO> resumedList(CustomFilters filters, Pageable pageSettings) {

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<HealthOperatorDTO> criteria = builder.createQuery(HealthOperatorDTO.class);
        Root<HealthOperator> root = criteria.from(HealthOperator.class);

        criteria.select(builder.construct(HealthOperatorDTO.class,
                                          root.get(HealthOperator_.id),
                                          root.get(HealthOperator_.ansCode),
                                          root.get(HealthOperator_.cnpj),
                                          root.get(HealthOperator_.companyName),
                                          root.get(HealthOperator_.fantasyName),
                                          root.get(HealthOperator_.modality)));
        return getPage(filters, pageSettings, builder, criteria, root);
    }

    @Override
    protected Predicate[] createRestrictions(CustomFilters customFilters, CriteriaBuilder builder, Root<HealthOperator> root) {
        if (!(customFilters instanceof HealthOperatorFilters filters)) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "Tipo de filtro inválido para Operadoras de saúde!");
        }
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isNullOrEmpty(filters.getId()) && StringUtils.isNumeric(filters.getId())) {
            predicates.add(builder.equal(root.get(HealthOperator_.id), Long.parseLong(filters.getId())));
        }
        if (!StringUtils.isNullOrEmpty(filters.getAnsCode())) {
            predicates.add(builder.equal(root.get(HealthOperator_.ansCode), Long.parseLong(filters.getAnsCode())));
        }
        if (!StringUtils.isNullOrEmpty(filters.getCnpj())) {
            predicates.add(builder.equal(root.get(HealthOperator_.cnpj), filters.getCnpj()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getCompanyName())) {
            predicates.add(builder.like(root.get(HealthOperator_.companyName), filters.getCompanyName()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getFantasyName())) {
            predicates.add(builder.like(root.get(HealthOperator_.fantasyName), filters.getFantasyName()));
        }
        if (filters.getModality() != null) {
            predicates.add(builder.equal(root.get(HealthOperator_.modality), filters.getModality()));
        }
        return predicates.toArray(new Predicate[predicates.size()]);
    }
}
