package br.com.nivlabs.cliniv.repository.custom.speciality;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import br.com.nivlabs.cliniv.controller.filters.SpecialityFilter;
import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.Speciality;
import br.com.nivlabs.cliniv.models.domain.Speciality_;
import br.com.nivlabs.cliniv.models.dto.SpecialityDTO;
import br.com.nivlabs.cliniv.repository.custom.CustomFilters;
import br.com.nivlabs.cliniv.repository.custom.GenericCustomRepository;
import br.com.nivlabs.cliniv.util.StringUtils;

/**
 * Repositório de Especialidades dos profissionais
 *
 * @author viniciosarodrigues
 */
public class SpecialityRepositoryCustomImpl extends GenericCustomRepository<Speciality, SpecialityDTO>
        implements SpecialityRepositoryCustom {

    @Override
    public Page<SpecialityDTO> resumedList(CustomFilters filters) {

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<SpecialityDTO> criteria = builder.createQuery(SpecialityDTO.class);
        Root<Speciality> root = criteria.from(Speciality.class);

        criteria.select(builder.construct(SpecialityDTO.class,
                root.get("id"),
                root.get("name")));
        return getPage(filters, builder, criteria, root);
    }

    @Override
    protected Predicate[] createRestrictions(CustomFilters customFilters, CriteriaBuilder builder, Root<Speciality> root) {
        if (!(customFilters instanceof SpecialityFilter filters)) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "O filtro enviado não é um filtro de especialidade");
        }
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isNullOrEmpty(filters.getId()) && StringUtils.isNumeric(filters.getId())) {
            predicates.add(builder.equal(root.get("id"), Long.parseLong(filters.getId())));
        }
        if (!StringUtils.isNullOrEmpty(filters.getName())) {
            predicates.add(builder.like(root.get("name"), filters.getName()));
        }

        return predicates.toArray(new Predicate[0]);
    }

}
