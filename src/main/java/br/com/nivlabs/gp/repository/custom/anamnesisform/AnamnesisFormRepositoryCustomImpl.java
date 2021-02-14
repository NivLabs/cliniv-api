package br.com.nivlabs.gp.repository.custom.anamnesisform;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import br.com.nivlabs.gp.controller.filters.DynamicFormFilters;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.DynamicForm;
import br.com.nivlabs.gp.models.domain.AnamnesisForm_;
import br.com.nivlabs.gp.models.dto.DynamicFormDTO;
import br.com.nivlabs.gp.repository.custom.CustomFilters;
import br.com.nivlabs.gp.repository.custom.GenericCustomRepository;
import br.com.nivlabs.gp.util.StringUtils;

/**
 * Repositório customizado para filtragem paginada de formulários de anamnese
 * 
 * @author viniciosarodrigues
 *
 */
public class AnamnesisFormRepositoryCustomImpl extends GenericCustomRepository<DynamicForm, DynamicFormDTO>
        implements AnamnesisFormRepositoryCustom {

    @Override
    public Page<DynamicFormDTO> resumedList(CustomFilters filters, Pageable pageSettings) {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<DynamicFormDTO> criteria = builder.createQuery(DynamicFormDTO.class);
        Root<DynamicForm> root = criteria.from(DynamicForm.class);

        criteria.select(builder.construct(DynamicFormDTO.class,
                                          root.get(AnamnesisForm_.id),
                                          root.get(AnamnesisForm_.title)));

        return getPage(filters, pageSettings, builder, criteria, root);
    }

    @Override
    protected Predicate[] createRestrictions(CustomFilters customFilters, CriteriaBuilder builder, Root<DynamicForm> root) {
        if (!(customFilters instanceof DynamicFormFilters)) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "O filtro enviado não é um filtro de atendimento");
        }
        DynamicFormFilters filters = (DynamicFormFilters) customFilters;
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isNullOrEmpty(filters.getId()) && StringUtils.isNumeric(filters.getId())) {
            predicates.add(builder.equal(root.get(AnamnesisForm_.id), Long.parseLong(filters.getId())));
        }
        if (!StringUtils.isNullOrEmpty(filters.getTitle())) {
            predicates.add(builder.like(root.get(AnamnesisForm_.title), filters.getTitle()));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

}
