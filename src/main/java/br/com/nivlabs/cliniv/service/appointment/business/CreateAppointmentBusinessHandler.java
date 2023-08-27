package br.com.nivlabs.cliniv.service.appointment.business;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.nivlabs.cliniv.models.domain.Appointment;
import br.com.nivlabs.cliniv.models.dto.AppointmentInfoDTO;

@Component
public class CreateAppointmentBusinessHandler extends CreateOrUpdateAppointmentBaseBusinessHandler {

    @Autowired
    private Logger logger;

    @Override
    public AppointmentInfoDTO execute(AppointmentInfoDTO request) {
        logger.info("Iniciando processo de criação de agendamento");
        request.setId(null); 
        super.validateRequest(null, request);
        Appointment entity = convertObject(request);
        entity = principalRepo.saveAndFlush(entity);
        request.setId(entity.getId());
        return request;
    }

}
