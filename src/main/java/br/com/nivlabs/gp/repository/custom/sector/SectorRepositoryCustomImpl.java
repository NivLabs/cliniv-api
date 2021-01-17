package br.com.nivlabs.gp.repository.custom.sector;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import br.com.nivlabs.gp.controller.filters.SectorFilters;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.Sector;
import br.com.nivlabs.gp.models.domain.Sector_;
import br.com.nivlabs.gp.models.dto.SectorDTO;
import br.com.nivlabs.gp.repository.custom.CustomFilters;
import br.com.nivlabs.gp.repository.custom.GenericCustomRepository;
import br.com.nivlabs.gp.util.StringUtils;

/**
 * Repositório de Profissionais e responsáveis customizado
 * 
 * @author viniciosarodrigues
 *
 */
public class SectorRepositoryCustomImpl extends GenericCustomRepository<Sector, SectorDTO> implements SectorRepositoryCustom {

    @Override
    public Page<SectorDTO> resumedList(CustomFilters filters, Pageable pageSettings) {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<SectorDTO> criteria = builder.createQuery(resumedClass);
        Root<Sector> root = criteria.from(persistentClass);

        criteria.select(builder.construct(resumedClass,
                                          root.get(Sector_.id),
                                          root.get(Sector_.description)));
        return getPage(filters, pageSettings, builder, criteria, root);
    }

    @Override
    protected Predicate[] createRestrictions(CustomFilters customFilters, CriteriaBuilder builder, Root<Sector> root) {
        if (!(customFilters instanceof SectorFilters)) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "O filtro enviado não é um filtro de setor");
        }
        SectorFilters filters = (SectorFilters) customFilters;
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isNullOrEmpty(filters.getId()) && StringUtils.isNumeric(filters.getId())) {
            predicates.add(builder.equal(root.get(Sector_.id), Long.parseLong(filters.getId())));
        }
        if (!StringUtils.isNullOrEmpty(filters.getDescription())) {
            predicates.add(builder.like(root.get(Sector_.description), filters.getDescription()));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

}
