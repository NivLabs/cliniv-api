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

import br.com.nivlabs.gp.controller.filters.AnamnesisFormFilters;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.AnamnesisForm;
import br.com.nivlabs.gp.models.domain.AnamnesisForm_;
import br.com.nivlabs.gp.models.dto.AnamnesisFormDTO;
import br.com.nivlabs.gp.repository.custom.CustomFilters;
import br.com.nivlabs.gp.repository.custom.GenericCustomRepository;
import br.com.nivlabs.gp.util.StringUtils;

/**
 * Repositório customizado para filtragem paginada de formulários de anamnese
 * 
 * @author viniciosarodrigues
 *
 */
public class AnamnesisFormRepositoryCustomImpl extends GenericCustomRepository<AnamnesisForm, AnamnesisFormDTO>
        implements AnamnesisFormRepositoryCustom {

    @Override
    public Page<AnamnesisFormDTO> resumedList(CustomFilters filters, Pageable pageSettings) {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<AnamnesisFormDTO> criteria = builder.createQuery(AnamnesisFormDTO.class);
        Root<AnamnesisForm> root = criteria.from(AnamnesisForm.class);

        criteria.select(builder.construct(AnamnesisFormDTO.class,
                                          root.get(AnamnesisForm_.id),
                                          root.get(AnamnesisForm_.title)));

        return getPage(filters, pageSettings, builder, criteria, root);
    }

    @Override
    protected Predicate[] createRestrictions(CustomFilters customFilters, CriteriaBuilder builder, Root<AnamnesisForm> root) {
        if (!(customFilters instanceof AnamnesisFormFilters)) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "O filtro enviado não é um filtro de atendimento");
        }
        AnamnesisFormFilters filters = (AnamnesisFormFilters) customFilters;
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
