package br.com.nivlabs.cliniv.repository.custom.responsible;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import br.com.nivlabs.cliniv.controller.filters.ResponsibleFilters;
import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.Person_;
import br.com.nivlabs.cliniv.models.domain.Responsible;
import br.com.nivlabs.cliniv.models.domain.Responsible_;
import br.com.nivlabs.cliniv.models.dto.ResponsibleDTO;
import br.com.nivlabs.cliniv.repository.custom.CustomFilters;
import br.com.nivlabs.cliniv.repository.custom.GenericCustomRepository;
import br.com.nivlabs.cliniv.util.StringUtils;

/**
 * Repositório de Profissionais e responsáveis customizado
 *
 * @author viniciosarodrigues
 */
public class ResponsibleRepositoryCustomImpl extends GenericCustomRepository<Responsible, ResponsibleDTO>
        implements ResponsibleRepositoryCustom {

    @Override
    public Page<ResponsibleDTO> resumedList(CustomFilters filters) {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<ResponsibleDTO> criteria = builder.createQuery(ResponsibleDTO.class);
        Root<Responsible> root = criteria.from(Responsible.class);

        criteria.select(builder.construct(ResponsibleDTO.class,
                root.get("id"),
                root.get("person").get("fullName"),
                root.get("person").get("socialName"),
                root.get("person").get("cpf"),
                root.get("person").get("bornDate"),
                root.get("person").get("principalNumber"),
                root.get("person").get("gender"),
                root.get("professionalIdentity"),
                root.get("initialsIdentity")));
        return getPage(filters, builder, criteria, root);
    }

    @Override
    protected Predicate[] createRestrictions(CustomFilters customFilters, CriteriaBuilder builder, Root<Responsible> root) {
        if (!(customFilters instanceof ResponsibleFilters filters)) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "O filtro enviado não é um filtro de profissional");
        }
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isNullOrEmpty(filters.getId()) && StringUtils.isNumeric(filters.getId())) {
            predicates.add(builder.equal(root.get("id"), Long.parseLong(filters.getId())));
        }
        if (!StringUtils.isNullOrEmpty(filters.getProfessionalIdentity())) {
            predicates.add(builder.equal(root.get("professionalIdentity"),
                    filters.getProfessionalIdentity()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getCpf())) {
            predicates.add(builder.equal(root.get("person").get("cpf"), filters.getCpf()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getFullName())) {
            predicates.add(builder.like(root.get("person").get("fullName"),
                    filters.getFullName()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getSocialName())) {
            predicates.add(builder.like(root.get("person").get("socialName"), filters.getSocialName()));
        }

        return predicates.toArray(new Predicate[0]);
    }

}
