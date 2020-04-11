package br.com.ft.gdp.repository.custom.patient;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.com.ft.gdp.controller.filters.PatientFilters;
import br.com.ft.gdp.models.domain.Patient;
import br.com.ft.gdp.models.dto.PatientDTO;
import br.com.ft.gdp.repository.custom.CustomFilters;
import br.com.ft.gdp.repository.custom.GenericCustomRepository;
import br.com.ft.gdp.repository.custom.IExpression;
import br.com.ft.gdp.util.StringUtils;

/**
 * Implementação de repositório customizado
 * 
 * @author viniciosarodrigues
 *
 */
public class PatientRepositoryCustomImpl extends GenericCustomRepository<Patient> implements PatientRepositoryCustom {

    @Override
    public Page<PatientDTO> resumedList(PatientFilters filters, Pageable pageSettings) {
        Page<Patient> pageFromDatabase = pagination(createRestrictions(filters), pageSettings);

        List<PatientDTO> listOfPatientDTO = new ArrayList<>();

        pageFromDatabase.forEach(responsible -> {
            PatientDTO patientConverted = new PatientDTO();
            BeanUtils.copyProperties(responsible.getPerson(), patientConverted, "id");
            BeanUtils.copyProperties(responsible, patientConverted);
            listOfPatientDTO.add(patientConverted);
        });
        return new PageImpl<>(listOfPatientDTO, pageSettings, pageFromDatabase.getTotalElements());
    }

    @Override
    protected List<IExpression<Patient>> createRestrictions(CustomFilters customFilters) {
        PatientFilters filters = (PatientFilters) customFilters;

        List<IExpression<Patient>> attributes = new ArrayList<>();

        if (!StringUtils.isNullOrEmpty(filters.getId()) && StringUtils.isNoFloatNumeric(filters.getId())) {
            attributes.add((cb, from) -> cb.equal(from.get("id"), Long.parseLong(filters.getId())));
        }
        if (!StringUtils.isNullOrEmpty(filters.getCpf())) {
            attributes.add((cb, from) -> cb.equal(from.get("person").get("cpf"), filters.getCpf()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getFirstName())) {
            attributes.add((cb, from) -> cb.like(from.get("person").get("firstName"), filters.getFirstName()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getLastName())) {
            attributes.add((cb, from) -> cb.like(from.get("person").get("lastName"), filters.getLastName()));
        }

        return attributes;
    }

}
