package br.com.nivlabs.cliniv.repository.custom.anamnesisform;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import br.com.nivlabs.cliniv.controller.filters.DynamicFormFilters;
import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.DynamicForm;
import br.com.nivlabs.cliniv.models.domain.DynamicForm_;
import br.com.nivlabs.cliniv.models.dto.DynamicFormDTO;
import br.com.nivlabs.cliniv.repository.custom.CustomFilters;
import br.com.nivlabs.cliniv.repository.custom.GenericCustomRepository;
import br.com.nivlabs.cliniv.util.StringUtils;

/**
 * Repositório customizado para filtragem paginada de formulários de anamnese
 *
 * @author viniciosarodrigues
 */
public class DynamicFormRepositoryCustomImpl extends GenericCustomRepository<DynamicForm, DynamicFormDTO>
        implements DynamicFormRepositoryCustom {

    @Override
    public Page<DynamicFormDTO> resumedList(CustomFilters filters) {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<DynamicFormDTO> criteria = builder.createQuery(DynamicFormDTO.class);
        Root<DynamicForm> root = criteria.from(DynamicForm.class);

        criteria.select(builder.construct(DynamicFormDTO.class,
                root.get("id"),
                root.get("title")));

        return getPage(filters, builder, criteria, root);
    }

    @Override
    protected Predicate[] createRestrictions(CustomFilters customFilters, CriteriaBuilder builder, Root<DynamicForm> root) {
        if (!(customFilters instanceof DynamicFormFilters filters)) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "O filtro enviado não é um filtro de atendimento");
        }
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isNullOrEmpty(filters.getId()) && StringUtils.isNumeric(filters.getId())) {
            predicates.add(builder.equal(root.get("id"), Long.parseLong(filters.getId())));
        }
        if (!StringUtils.isNullOrEmpty(filters.getTitle())) {
            predicates.add(builder.like(root.get("title"), filters.getTitle()));
        }

        return predicates.toArray(new Predicate[0]);
    }

}
