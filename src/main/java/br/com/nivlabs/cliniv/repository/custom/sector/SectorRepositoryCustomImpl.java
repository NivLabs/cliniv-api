package br.com.nivlabs.cliniv.repository.custom.sector;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import br.com.nivlabs.cliniv.controller.filters.SectorFilters;
import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.Sector;
import br.com.nivlabs.cliniv.models.domain.Sector_;
import br.com.nivlabs.cliniv.models.dto.SectorDTO;
import br.com.nivlabs.cliniv.repository.custom.CustomFilters;
import br.com.nivlabs.cliniv.repository.custom.GenericCustomRepository;
import br.com.nivlabs.cliniv.util.StringUtils;

/**
 * Repositório de Profissionais e responsáveis customizado
 *
 * @author viniciosarodrigues
 */
public class SectorRepositoryCustomImpl extends GenericCustomRepository<Sector, SectorDTO> implements SectorRepositoryCustom {

    @Override
    public Page<SectorDTO> resumedList(CustomFilters filters) {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<SectorDTO> criteria = builder.createQuery(SectorDTO.class);
        Root<Sector> root = criteria.from(Sector.class);

        criteria.select(builder.construct(SectorDTO.class,
                root.get("id"),
                root.get("description")));
        return getPage(filters, builder, criteria, root);
    }

    @Override
    protected Predicate[] createRestrictions(CustomFilters customFilters, CriteriaBuilder builder, Root<Sector> root) {
        if (!(customFilters instanceof SectorFilters filters)) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "O filtro enviado não é um filtro de setor");
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
