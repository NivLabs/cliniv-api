package br.com.nivlabs.cliniv.service.appointment.business;

import br.com.nivlabs.cliniv.models.domain.Appointment;
import br.com.nivlabs.cliniv.models.dto.AppointmentInfoDTO;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Component
public class CreateAppointmentBusinessHandler extends CreateOrUpdateAppointmentBaseBusinessHandler {

    @Autowired
    private Logger logger;

    @Override
    public AppointmentInfoDTO execute(final AppointmentInfoDTO request) {
        logger.info("Iniciando processo de criação de agendamento");
        request.setId(null);
        super.validateRequest(null, request);
        Appointment entity = convertObject(request);
        if (request.getRepeatSettings() != null && request.getRepeatSettings().getNumberOfOccurrences() > 0) {
            applyRecurrence(request);
        }
        principalRepo.saveAndFlush(entity);
        request.setId(entity.getId());
        return request;
    }

    /**
     * Gera agenda recursiva
     *
     * @param request Reqyisição de geração de agendamento
     */
    private void applyRecurrence(AppointmentInfoDTO request) {
        final var schedulingDate = request.getSchedulingDateAndTime();
        final var occurrenceQuantity = request.getRepeatSettings().getNumberOfOccurrences();
        final var isBusinessDaysOnly = request.getRepeatSettings().getBusinessDaysOnly();
        logger.info("Iniciando processo de geração de agendamento recorrentes...");
        logger.info("Data do primeiro agendamento :: {}", schedulingDate);
        logger.info("Quantidade de ocorrências :: {}", occurrenceQuantity);
        logger.info("Apenas dias úteis? :: {}", isBusinessDaysOnly ? "SIM" : "NÃO");
        LocalDateTime newDateTime = schedulingDate;
        for (int i = 0; i < occurrenceQuantity; i++) {
            switch (request.getRepeatSettings().getIntervalType()) {
                case DAILY -> newDateTime = newDateTime.plusDays(1);
                case WEEKLY -> newDateTime = newDateTime.plusWeeks(1);
                case MONTHLY -> newDateTime = newDateTime.plusMonths(1);
                case YEARLY -> newDateTime = newDateTime.plusYears(1);
            }
            if (isBusinessDaysOnly) {
                if (newDateTime.getDayOfWeek() == DayOfWeek.SATURDAY) {
                    newDateTime = newDateTime.plusDays(2);
                    logger.info("O dia selecionado é um sábado, a marcação será reagendada para o próximo dia útil :: {}", newDateTime);
                } else if (newDateTime.getDayOfWeek() == DayOfWeek.SUNDAY) {
                    newDateTime = newDateTime.plusDays(1);
                    logger.info("O dia selecionado é um domingo, a marcação será reagendada para o próximo dia útil :: {}", newDateTime);
                }
            }
            var entity = convertObject(request);
            entity.setAppointmentDateAndTime(newDateTime);
            principalRepo.saveAndFlush(entity);
            logger.info("Agendamento para o dia/hora :: {}", newDateTime);
        }
    }
}
