package br.com.nivlabs.gp.repository.custom.schedule;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import br.com.nivlabs.gp.controller.filters.ScheduleFilters;
import br.com.nivlabs.gp.enums.DocumentType;
import br.com.nivlabs.gp.enums.ScheduleStatus;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.Patient;
import br.com.nivlabs.gp.models.domain.Patient_;
import br.com.nivlabs.gp.models.domain.Person;
import br.com.nivlabs.gp.models.domain.Person_;
import br.com.nivlabs.gp.models.domain.Responsible;
import br.com.nivlabs.gp.models.domain.Responsible_;
import br.com.nivlabs.gp.models.domain.Schedule;
import br.com.nivlabs.gp.models.domain.Schedule_;
import br.com.nivlabs.gp.models.dto.AddressDTO;
import br.com.nivlabs.gp.models.dto.DocumentDTO;
import br.com.nivlabs.gp.models.dto.HealthPlanDTO;
import br.com.nivlabs.gp.models.dto.PatientInfoDTO;
import br.com.nivlabs.gp.models.dto.ProfessionalIdentityDTO;
import br.com.nivlabs.gp.models.dto.ResponsibleInfoDTO;
import br.com.nivlabs.gp.models.dto.ScheduleInfoDTO;
import br.com.nivlabs.gp.repository.custom.CustomFilters;
import br.com.nivlabs.gp.repository.custom.GenericCustomRepository;
import br.com.nivlabs.gp.repository.custom.IExpression;
import br.com.nivlabs.gp.util.StringUtils;

public class ScheduleRepositoryCustomImpl extends GenericCustomRepository<Schedule> implements ScheduleRepositoryCustom {

    @Override
    public Page<ScheduleInfoDTO> resumedList(CustomFilters filters, Pageable pageSettings) {
        // TODO: Revisar esta rotina em breve
        filters.setSize(100);
        Page<Schedule> pageFromDatabase = pagination(createRestrictions(filters), pageSettings);
        List<ScheduleInfoDTO> listOfScheduleFromDb = new ArrayList<>();

        pageFromDatabase.forEach(schedule -> {

            ScheduleInfoDTO converted = new ScheduleInfoDTO();
            BeanUtils.copyProperties(schedule, converted);

            converted.setPatient(convertPatient(schedule.getPatient()));
            converted.setProfessional(convertResponsible(schedule.getProfessional()));

            listOfScheduleFromDb.add(converted);

        });
        return new PageImpl<>(listOfScheduleFromDb, pageSettings, pageFromDatabase.getTotalElements());
    }

    @Override
    protected List<IExpression<Schedule>> createRestrictions(CustomFilters customFilters) {
        ScheduleFilters filters = (ScheduleFilters) customFilters;

        List<IExpression<Schedule>> attributes = new ArrayList<>();

        if (!StringUtils.isNullOrEmpty(filters.getProfessionalId()) && StringUtils.isNumeric(filters.getProfessionalId())) {
            attributes.add((cb, from) -> cb.equal(from.get(Schedule_.professional).get(Responsible_.id),
                                                  Long.parseLong(filters.getProfessionalId())));
        }
        if (filters.getSelectedDate() != null) {
            attributes.add((cb, from) -> cb
                    .and(cb.greaterThan(from.get(Schedule_.schedulingDateAndTime),
                                        LocalDateTime.of(filters.getSelectedDate(), LocalTime.MIN)),
                         cb.lessThan(from.get(Schedule_.schedulingDateAndTime),
                                     LocalDateTime.of(filters.getSelectedDate(), LocalTime.MAX))));
        }
        if (!StringUtils.isNullOrEmpty(filters.getStatus())) {
            attributes.add((cb, from) -> cb.equal(from.get(Schedule_.status), handleScheduleStatus(filters.getStatus())));
        }

        return attributes;
    }

    private ScheduleStatus handleScheduleStatus(String statusFromRequest) {
        try {
            return ScheduleStatus.valueOf(statusFromRequest.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new HttpException(HttpStatus.BAD_REQUEST,
                    "Não foi possível processar a requisição. A situação informada para o agendamento não existe :: " + statusFromRequest);
        }
    }

    /**
     * Converte os objetos internos em DTO
     * 
     * @param responsibleOrigin
     * @return
     */
    private static ResponsibleInfoDTO convertResponsible(Responsible responsibleOrigin) {
        Person person = responsibleOrigin.getPerson();

        ResponsibleInfoDTO responsibleConverted = new ResponsibleInfoDTO();
        BeanUtils.copyProperties(responsibleOrigin.getPerson(), responsibleConverted, Person_.ID);
        BeanUtils.copyProperties(responsibleOrigin, responsibleConverted);
        responsibleConverted.setDocument(new DocumentDTO(DocumentType.CPF, person.getCpf()));

        if (person.getAddress() != null) {
            AddressDTO address = new AddressDTO();
            BeanUtils.copyProperties(person.getAddress(), address);
            responsibleConverted.setAddress(address);
        }
        if (responsibleOrigin.getProfessionalIdentity() != null) {
            ProfessionalIdentityDTO professionalIdentity = new ProfessionalIdentityDTO();
            professionalIdentity.setRegisterValue(responsibleOrigin.getProfessionalIdentity());
            if (responsibleOrigin.getInitialsIdentity() != null) {
                professionalIdentity.setRegisterType(responsibleOrigin.getInitialsIdentity());
            }
            responsibleConverted.setProfessionalIdentity(professionalIdentity);
        }

        responsibleConverted.setId(responsibleOrigin.getId());

        return responsibleConverted;
    }

    /**
     * Converte os objetos internos em DTO
     * 
     * @param patient
     * @return
     */
    private PatientInfoDTO convertPatient(Patient patient) {
        Person person = patient.getPerson();
        PatientInfoDTO patientInfo = new PatientInfoDTO();
        BeanUtils.copyProperties(person, patientInfo, Person_.ID);
        patientInfo.setDocument(new DocumentDTO(DocumentType.CPF, person.getCpf()));

        if (person.getAddress() != null) {
            AddressDTO address = new AddressDTO();
            BeanUtils.copyProperties(person.getAddress(), address);
            patientInfo.setAddress(address);
        }
        if (patient.getHealthPlan() != null) {
            HealthPlanDTO healthPlan = new HealthPlanDTO();
            BeanUtils.copyProperties(patient.getHealthPlan(), healthPlan);
            healthPlan.setPatientPlanNumber(patient.getHealthPlanCode());
            healthPlan.setOperatorCode(patient.getHealthPlan().getHealthOperator().getAnsCode());
            healthPlan.setOperatorName(patient.getHealthPlan().getHealthOperator().getFantasyName());
            patientInfo.setHealthPlan(healthPlan);
        }
        BeanUtils.copyProperties(patient, patientInfo, Patient_.ALLERGIES);
        patient.getAllergies().forEach(allergy -> patientInfo.getAllergies().add(allergy.getDescription()));
        return patientInfo;
    }
}
