package br.com.nivlabs.cliniv.repository.custom.patient;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
 *
 */
public class PatientRepositoryCustomImpl extends GenericCustomRepository<Patient, PatientDTO> implements PatientRepositoryCustom {

    @Override
    public Page<PatientDTO> resumedList(CustomFilters filters) {

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<PatientDTO> criteria = builder.createQuery(PatientDTO.class);
        Root<Patient> root = criteria.from(Patient.class);

        criteria.select(builder.construct(PatientDTO.class,
                                          root.get(Patient_.id),
                                          root.get(Patient_.person).get(Person_.fullName),
                                          root.get(Patient_.person).get(Person_.socialName),
                                          root.get(Patient_.person).get(Person_.cpf),
                                          root.get(Patient_.person).get(Person_.bornDate),
                                          root.get(Patient_.person).get(Person_.principalNumber),
                                          root.get(Patient_.person).get(Person_.gender),
                                          root.get(Patient_.cnsNumber),
                                          root.get(Patient_.type)));
        return getPage(filters, builder, criteria, root);
    }

    @Override
    protected Predicate[] createRestrictions(CustomFilters customFilters, CriteriaBuilder builder, Root<Patient> root) {
        if (!(customFilters instanceof PatientFilters filters)) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "O filtro enviado não é um filtro de paciente");
        }
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isNullOrEmpty(filters.getId()) && StringUtils.isNumeric(filters.getId())) {
            predicates.add(builder.equal(root.get(Patient_.id), Long.parseLong(filters.getId())));
        }
        if (!StringUtils.isNullOrEmpty(filters.getCnsNumber()) && StringUtils.isNumeric(filters.getCnsNumber())) {
            predicates.add(builder.equal(root.get(Patient_.cnsNumber), filters.getCnsNumber()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getCpf())) {
            predicates.add(builder.equal(root.get(Patient_.person).get(Person_.cpf), filters.getCpf()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getFullName())) {
            predicates.add(builder.like(root.get(Patient_.person).get(Person_.fullName), filters.getFullName()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getSocialName())) {
            predicates.add(builder.like(root.get(Patient_.person).get(Person_.socialName), filters.getSocialName()));
        }
        if (filters.getType() != null) {
            predicates.add(builder.equal(root.get(Patient_.type), filters.getType()));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

}
