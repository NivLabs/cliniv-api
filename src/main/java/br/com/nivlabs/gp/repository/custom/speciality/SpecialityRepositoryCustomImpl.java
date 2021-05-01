package br.com.nivlabs.gp.repository.custom.speciality;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import br.com.nivlabs.gp.controller.filters.SpecialityFilter;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.Speciality;
import br.com.nivlabs.gp.models.domain.Speciality_;
import br.com.nivlabs.gp.models.dto.SpecialityDTO;
import br.com.nivlabs.gp.repository.custom.CustomFilters;
import br.com.nivlabs.gp.repository.custom.GenericCustomRepository;
import br.com.nivlabs.gp.util.StringUtils;

/**
 * Repositório de Especialidades dos profissionais
 * 
 * @author viniciosarodrigues
 *
 */
public class SpecialityRepositoryCustomImpl extends GenericCustomRepository<Speciality, SpecialityDTO>
        implements SpecialityRepositoryCustom {

    @Override
    public Page<SpecialityDTO> resumedList(CustomFilters filters, Pageable pageSettings) {

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<SpecialityDTO> criteria = builder.createQuery(SpecialityDTO.class);
        Root<Speciality> root = criteria.from(Speciality.class);

        criteria.select(builder.construct(SpecialityDTO.class,
                                          root.get(Speciality_.id),
                                          root.get(Speciality_.name)));
        return getPage(filters, pageSettings, builder, criteria, root);
    }

    @Override
    protected Predicate[] createRestrictions(CustomFilters customFilters, CriteriaBuilder builder, Root<Speciality> root) {
        if (!(customFilters instanceof SpecialityFilter filters)) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "O filtro enviado não é um filtro de especialidade");
        }
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isNullOrEmpty(filters.getId()) && StringUtils.isNumeric(filters.getId())) {
            predicates.add(builder.equal(root.get(Speciality_.id), Long.parseLong(filters.getId())));
        }
        if (!StringUtils.isNullOrEmpty(filters.getName())) {
            predicates.add(builder.like(root.get(Speciality_.name), filters.getName()));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

}
