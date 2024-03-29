package br.com.nivlabs.cliniv.repository.custom.allergy;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import br.com.nivlabs.cliniv.controller.filters.AllergyFilters;
import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.Allergy;
import br.com.nivlabs.cliniv.models.domain.Allergy_;
import br.com.nivlabs.cliniv.models.dto.AllergyDTO;
import br.com.nivlabs.cliniv.repository.custom.CustomFilters;
import br.com.nivlabs.cliniv.repository.custom.GenericCustomRepository;
import br.com.nivlabs.cliniv.util.StringUtils;

/**
 * Repositório customizado para buscas paginadas de alergias
 *
 * @author viniciosarodrigues
 */
public class AllergyRepositoryCustomImpl extends GenericCustomRepository<Allergy, AllergyDTO> implements AllergyRepositoryCustom {

    @Override
    public Page<AllergyDTO> resumedList(CustomFilters filters) {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<AllergyDTO> criteria = builder.createQuery(AllergyDTO.class);
        Root<Allergy> root = criteria.from(Allergy.class);

        criteria.select(builder.construct(AllergyDTO.class,
                root.get("id"),
                root.get("description")));
        return getPage(filters, builder, criteria, root);
    }

    @Override
    protected Predicate[] createRestrictions(CustomFilters customFilters, CriteriaBuilder builder, Root<Allergy> root) {
        if (!(customFilters instanceof AllergyFilters filters)) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "O filtro enviado não é um filtro de alergias");
        }

        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isNullOrEmpty(filters.getDescription())) {
            predicates.add(builder.like(root.get("description"), filters.getDescription()));
        }

        return predicates.toArray(new Predicate[0]);
    }
}
