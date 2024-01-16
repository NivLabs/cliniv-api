package br.com.nivlabs.cliniv.repository.custom.schedule;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import br.com.nivlabs.cliniv.controller.filters.AppointmentFilters;
import br.com.nivlabs.cliniv.enums.AppointmentStatus;
import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.Appointment;
import br.com.nivlabs.cliniv.models.domain.Appointment_;
import br.com.nivlabs.cliniv.models.domain.Patient_;
import br.com.nivlabs.cliniv.models.domain.Person_;
import br.com.nivlabs.cliniv.models.domain.Responsible_;
import br.com.nivlabs.cliniv.models.dto.AppointmentDTO;
import br.com.nivlabs.cliniv.repository.custom.CustomFilters;
import br.com.nivlabs.cliniv.repository.custom.GenericCustomRepository;
import br.com.nivlabs.cliniv.util.StringUtils;

/**
 * Implementação customizada de paginação para agendamentos
 *
 * @author viniciosarodrigues
 */
public class AppointmentRepositoryCustomImpl extends GenericCustomRepository<Appointment, AppointmentDTO>
        implements AppointmentRepositoryCustom {
    @Override
    public Page<AppointmentDTO> resumedList(CustomFilters filters) {

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<AppointmentDTO> criteria = builder.createQuery(AppointmentDTO.class);
        Root<Appointment> root = criteria.from(Appointment.class);

        criteria.select(builder.construct(AppointmentDTO.class,
                root.get("id"),
                root.get("patient").get("person")
                        .get("fullName"),
                root.get("patient").get("person").get("cpf"),
                root.get("professional").get("id"),
                root.get("professional").get("person").get("fullName"),
                root.get("appointmentDateAndTime"),
                root.get("status")));
        return getPage(filters, builder, criteria, root);
    }

    @Override
    protected Predicate[] createRestrictions(CustomFilters customFilters, CriteriaBuilder builder, Root<Appointment> root) {
        if (!(customFilters instanceof AppointmentFilters filters)) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "O filtro enviado não é um filtro de agendamento");
        }
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isNullOrEmpty(filters.getProfessionalId()) && StringUtils.isNumeric(filters.getProfessionalId())) {
            predicates.add(builder.equal(root.get("professional").get("id"),
                    Long.parseLong(filters.getProfessionalId())));
        }
        predicates.add(builder.and(builder.greaterThan(root.get("appointmentDateAndTime"),
                        LocalDateTime.of(filters.getStartDate(), LocalTime.MIN)),
                builder.lessThan(root.get("appointmentDateAndTime"),
                        LocalDateTime.of(filters.getEndDate(), LocalTime.MAX))));
        if (!StringUtils.isNullOrEmpty(filters.getStatus())) {
            predicates.add(builder.equal(root.get("status"), handleScheduleStatus(filters.getStatus())));
        }

        return predicates.toArray(new Predicate[0]);
    }

    private AppointmentStatus handleScheduleStatus(String statusFromRequest) {
        try {
            return AppointmentStatus.valueOf(statusFromRequest.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new HttpException(HttpStatus.BAD_REQUEST,
                    "Não foi possível processar a requisição. A situação informada para o agendamento não existe :: " + statusFromRequest);
        }
    }

}