package br.com.nivlabs.gp.repository.custom.responsible;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import br.com.nivlabs.gp.controller.filters.ResponsibleFilters;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.Person_;
import br.com.nivlabs.gp.models.domain.Responsible;
import br.com.nivlabs.gp.models.domain.Responsible_;
import br.com.nivlabs.gp.models.dto.ResponsibleDTO;
import br.com.nivlabs.gp.repository.custom.CustomFilters;
import br.com.nivlabs.gp.repository.custom.GenericCustomRepository;
import br.com.nivlabs.gp.util.StringUtils;

/**
 * Repositório de Profissionais e responsáveis customizado
 * 
 * @author viniciosarodrigues
 *
 */
public class ResponsibleRepositoryCustomImpl extends GenericCustomRepository<Responsible, ResponsibleDTO>
        implements ResponsibleRepositoryCustom {

    @Override
    public Page<ResponsibleDTO> resumedList(CustomFilters filters, Pageable pageSettings) {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<ResponsibleDTO> criteria = builder.createQuery(ResponsibleDTO.class);
        Root<Responsible> root = criteria.from(Responsible.class);

        criteria.select(builder.construct(ResponsibleDTO.class,
                                          root.get(Responsible_.id),
                                          root.get(Responsible_.person).get(Person_.fullName),
                                          root.get(Responsible_.person).get(Person_.socialName),
                                          root.get(Responsible_.person).get(Person_.cpf),
                                          root.get(Responsible_.person).get(Person_.bornDate),
                                          root.get(Responsible_.person).get(Person_.principalNumber),
                                          root.get(Responsible_.person).get(Person_.gender),
                                          root.get(Responsible_.professionalIdentity),
                                          root.get(Responsible_.initialsIdentity)));
        return getPage(filters, pageSettings, builder, criteria, root);
    }

    @Override
    protected Predicate[] createRestrictions(CustomFilters customFilters, CriteriaBuilder builder, Root<Responsible> root) {
        if (!(customFilters instanceof ResponsibleFilters filters)) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "O filtro enviado não é um filtro de profissional");
        }
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isNullOrEmpty(filters.getId()) && StringUtils.isNumeric(filters.getId())) {
            predicates.add(builder.equal(root.get(Responsible_.id), Long.parseLong(filters.getId())));
        }
        if (!StringUtils.isNullOrEmpty(filters.getProfessionalIdentity())) {
            predicates.add(builder.equal(root.get(Responsible_.professionalIdentity),
                                         filters.getProfessionalIdentity()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getCpf())) {
            predicates.add(builder.equal(root.get(Responsible_.person).get(Person_.cpf), filters.getCpf()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getFullName())) {
            predicates.add(builder.like(root.get(Responsible_.person).get(Person_.fullName),
                                        filters.getFullName()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getSocialName())) {
            predicates.add(builder.like(root.get(Responsible_.person).get(Person_.socialName), filters.getSocialName()));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

}
