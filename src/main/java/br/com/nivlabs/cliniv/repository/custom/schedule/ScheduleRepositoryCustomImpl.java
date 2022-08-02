package br.com.nivlabs.cliniv.repository.custom.schedule;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import br.com.nivlabs.cliniv.controller.filters.ScheduleFilters;
import br.com.nivlabs.cliniv.enums.ScheduleStatus;
import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.Schedule;
import br.com.nivlabs.cliniv.models.dto.ScheduleDTO;
import br.com.nivlabs.cliniv.repository.custom.CustomFilters;
import br.com.nivlabs.cliniv.repository.custom.GenericCustomRepository;
import br.com.nivlabs.cliniv.util.StringUtils;
import br.com.nivlabs.cliniv.models.domain.Patient_;
import br.com.nivlabs.cliniv.models.domain.Person_;
import br.com.nivlabs.cliniv.models.domain.Responsible_;
import br.com.nivlabs.cliniv.models.domain.Schedule_;

/**
 * Implementação customizada de paginação para agendamentos
 * 
 * @author viniciosarodrigues
 *
 */
public class ScheduleRepositoryCustomImpl extends GenericCustomRepository<Schedule, ScheduleDTO> implements ScheduleRepositoryCustom {
    @Override
    public Page<ScheduleDTO> resumedList(CustomFilters filters, Pageable pageSettings) {

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<ScheduleDTO> criteria = builder.createQuery(ScheduleDTO.class);
        Root<Schedule> root = criteria.from(Schedule.class);

        criteria.select(builder.construct(ScheduleDTO.class,
                                          root.get(Schedule_.id),
                                          root.get(Schedule_.patient).get(Patient_.person)
                                                  .get(Person_.fullName),
                                          root.get(Schedule_.patient).get(Patient_.person).get(Person_.cpf),
                                          root.get(Schedule_.professional).get(Responsible_.id),
                                          root.get(Schedule_.professional).get(Responsible_.person).get(Person_.fullName),
                                          root.get(Schedule_.schedulingDateAndTime),
                                          root.get(Schedule_.status)));
        return getPage(filters, pageSettings, builder, criteria, root);
    }

    @Override
    protected Predicate[] createRestrictions(CustomFilters customFilters, CriteriaBuilder builder, Root<Schedule> root) {
        if (!(customFilters instanceof ScheduleFilters filters)) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "O filtro enviado não é um filtro de agendamento");
        }
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isNullOrEmpty(filters.getProfessionalId()) && StringUtils.isNumeric(filters.getProfessionalId())) {
            predicates.add(builder.equal(root.get(Schedule_.professional).get(Responsible_.id),
                                         Long.parseLong(filters.getProfessionalId())));
        }
        if (filters.getSelectedDate() != null) {
            predicates.add(builder.and(builder.greaterThan(root.get(Schedule_.schedulingDateAndTime),
                                                           LocalDateTime.of(filters.getSelectedDate(), LocalTime.MIN)),
                                       builder.lessThan(root.get(Schedule_.schedulingDateAndTime),
                                                        LocalDateTime.of(filters.getSelectedDate(), LocalTime.MAX))));
        }
        if (!StringUtils.isNullOrEmpty(filters.getStatus())) {
            predicates.add(builder.equal(root.get(Schedule_.status), handleScheduleStatus(filters.getStatus())));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

    private ScheduleStatus handleScheduleStatus(String statusFromRequest) {
        try {
            return ScheduleStatus.valueOf(statusFromRequest.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new HttpException(HttpStatus.BAD_REQUEST,
                    "Não foi possível processar a requisição. A situação informada para o agendamento não existe :: " + statusFromRequest);
        }
    }

}