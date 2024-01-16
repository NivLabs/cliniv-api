package br.com.nivlabs.cliniv.repository.custom.patient;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import br.com.nivlabs.cliniv.controller.filters.PatientFilters;
import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.Patient;
import br.com.nivlabs.cliniv.models.domain.Patient_;
import br.com.nivlabs.cliniv.models.domain.Person_;
import br.com.nivlabs.cliniv.models.dto.PatientDTO;
import br.com.nivlabs.cliniv.repository.custom.CustomFilters;
import br.com.nivlabs.cliniv.repository.custom.GenericCustomRepository;
import br.com.nivlabs.cliniv.util.StringUtils;

/**
 * Repositório customizado para buscas paginadas de pacientes
 *
 * @author viniciosarodrigues
 */
public class PatientRepositoryCustomImpl extends GenericCustomRepository<Patient, PatientDTO> implements PatientRepositoryCustom {

    @Override
    public Page<PatientDTO> resumedList(CustomFilters filters) {

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<PatientDTO> criteria = builder.createQuery(PatientDTO.class);
        Root<Patient> root = criteria.from(Patient.class);

        criteria.select(builder.construct(PatientDTO.class,
                root.get("id"),
                root.get("person").get("fullName"),
                root.get("person").get("socialName"),
                root.get("person").get("cpf"),
                root.get("person").get("bornDate"),
                root.get("person").get("principalNumber"),
                root.get("person").get("gender"),
                root.get("cnsNumber"),
                root.get("type")));
        return getPage(filters, builder, criteria, root);
    }

    @Override
    protected Predicate[] createRestrictions(CustomFilters customFilters, CriteriaBuilder builder, Root<Patient> root) {
        if (!(customFilters instanceof PatientFilters filters)) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "O filtro enviado não é um filtro de paciente");
        }
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isNullOrEmpty(filters.getId()) && StringUtils.isNumeric(filters.getId())) {
            predicates.add(builder.equal(root.get("id"), Long.parseLong(filters.getId())));
        }
        if (!StringUtils.isNullOrEmpty(filters.getCnsNumber()) && StringUtils.isNumeric(filters.getCnsNumber())) {
            predicates.add(builder.equal(root.get("cnsNumber"), filters.getCnsNumber()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getCpf())) {
            predicates.add(builder.equal(root.get("person").get("cpf"), filters.getCpf()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getFullName())) {
            predicates.add(builder.like(root.get("person").get("fullName"), filters.getFullName()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getSocialName())) {
            predicates.add(builder.like(root.get("person").get("socialName"), filters.getSocialName()));
        }
        if (filters.getType() != null) {
            predicates.add(builder.equal(root.get("type"), filters.getType()));
        }

        return predicates.toArray(new Predicate[0]);
    }

}
