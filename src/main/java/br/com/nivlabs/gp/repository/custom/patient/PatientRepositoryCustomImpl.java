package br.com.nivlabs.gp.repository.custom.patient;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.com.nivlabs.gp.controller.filters.PatientFilters;
import br.com.nivlabs.gp.models.domain.Patient;
import br.com.nivlabs.gp.models.domain.Patient_;
import br.com.nivlabs.gp.models.domain.Person_;
import br.com.nivlabs.gp.models.dto.HealthPlanDTO;
import br.com.nivlabs.gp.models.dto.PatientDTO;
import br.com.nivlabs.gp.repository.custom.CustomFilters;
import br.com.nivlabs.gp.repository.custom.GenericCustomRepository;
import br.com.nivlabs.gp.repository.custom.IExpression;
import br.com.nivlabs.gp.util.StringUtils;

/**
 * Implementação de repositório customizado
 * 
 * @author viniciosarodrigues
 *
 */
public class PatientRepositoryCustomImpl extends GenericCustomRepository<Patient> implements PatientRepositoryCustom {

    @Override
    public Page<PatientDTO> resumedList(CustomFilters filters, Pageable pageSettings) {
        Page<Patient> pageFromDatabase = pagination(createRestrictions(filters), pageSettings);

        List<PatientDTO> listOfPatientDTO = new ArrayList<>();

        pageFromDatabase.forEach(patient -> {
            PatientDTO patientConverted = new PatientDTO();
            BeanUtils.copyProperties(patient.getPerson(), patientConverted, "id");
            BeanUtils.copyProperties(patient, patientConverted);
            if (patient.getHealthPlan() != null) {
                HealthPlanDTO healthPlan = new HealthPlanDTO();
                BeanUtils.copyProperties(patient.getHealthPlan(), healthPlan);
                patientConverted.setHealthPlan(healthPlan);
            }

            listOfPatientDTO.add(patientConverted);
        });
        return new PageImpl<>(listOfPatientDTO, pageSettings, pageFromDatabase.getTotalElements());
    }

    @Override
    protected List<IExpression<Patient>> createRestrictions(CustomFilters customFilters) {
        PatientFilters filters = (PatientFilters) customFilters;

        List<IExpression<Patient>> attributes = new ArrayList<>();

        if (!StringUtils.isNullOrEmpty(filters.getId()) && StringUtils.isNumeric(filters.getId())) {
            attributes.add((cb, from) -> cb.equal(from.get(Patient_.id), Long.parseLong(filters.getId())));
        }

        if (!StringUtils.isNullOrEmpty(filters.getSusNumber()) && StringUtils.isNumeric(filters.getSusNumber())) {
            attributes.add((cb, from) -> cb.equal(from.get(Patient_.susNumber), filters.getSusNumber()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getCpf())) {
            attributes.add((cb, from) -> cb.equal(from.get(Patient_.person).get(Person_.cpf), filters.getCpf()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getFirstName())) {
            attributes.add(
                           (cb, from) -> cb.like(from.get(Patient_.person).get(Person_.firstName), filters.getFirstName()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getLastName())) {
            attributes
                    .add((cb, from) -> cb.like(from.get(Patient_.person).get(Person_.lastName), filters.getLastName()));
        }
        if (filters.getType() != null) {
            attributes.add((cb, from) -> cb.equal(from.get(Patient_.type), filters.getType()));
        }

        return attributes;
    }

}
