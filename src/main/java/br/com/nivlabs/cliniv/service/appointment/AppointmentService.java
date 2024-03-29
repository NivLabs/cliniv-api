package br.com.nivlabs.cliniv.service.appointment;

import br.com.nivlabs.cliniv.controller.filters.AppointmentFilters;
import br.com.nivlabs.cliniv.models.dto.AppointmentInfoDTO;
import br.com.nivlabs.cliniv.models.dto.AppointmentsResponseDTO;
import br.com.nivlabs.cliniv.service.BaseService;
import br.com.nivlabs.cliniv.service.appointment.business.CreateAppointmentBusinessHandler;
import br.com.nivlabs.cliniv.service.appointment.business.DeleteAppointmentBusinessHandler;
import br.com.nivlabs.cliniv.service.appointment.business.SearchAppointmentBusinessHandler;
import br.com.nivlabs.cliniv.service.appointment.business.UpdateAppointmentBusinessHandler;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Classe AppointmentsService.java
 *
 * @author viniciosarodrigues
 */
@Service
public class AppointmentService implements BaseService {

    private final Logger logger;

    private final CreateAppointmentBusinessHandler createAppointmentBusinessHandler;
    private final UpdateAppointmentBusinessHandler updateAppointmentBusinessHandler;
    private final SearchAppointmentBusinessHandler searchScheduleBusinessHandler;
    private final DeleteAppointmentBusinessHandler deleteAppointmentBusinessHandler;

    @Autowired
    public AppointmentService(
            final Logger logger,
            final CreateAppointmentBusinessHandler createAppointmentBusinessHandler,
            final UpdateAppointmentBusinessHandler updateAppointmentBusinessHandler,
            final SearchAppointmentBusinessHandler searchScheduleBusinessHandler,
            final DeleteAppointmentBusinessHandler deleteAppointmentBusinessHandler) {
        this.logger = logger;
        this.createAppointmentBusinessHandler = createAppointmentBusinessHandler;
        this.updateAppointmentBusinessHandler = updateAppointmentBusinessHandler;
        this.searchScheduleBusinessHandler = searchScheduleBusinessHandler;
        this.deleteAppointmentBusinessHandler = deleteAppointmentBusinessHandler;
    }


    /**
     * Realiza uma busca filtrada de agendamentos baseado na data
     *
     * @param filters Filtros da requisição (Query Param)
     * @return Objeto com lista filtrada de Agendamentos e dias do mês com agendamentos marcados
     */
    public AppointmentsResponseDTO findByFilters(AppointmentFilters filters) {
        return searchScheduleBusinessHandler.find(filters);
    }

    /**
     * Busca agendamento por identificador único
     *
     * @param id Identificador único do agendamento
     * @return Informações detalhadas do agendamento
     */
    public AppointmentInfoDTO findById(Long id) {
        return searchScheduleBusinessHandler.byId(id);
    }

    /**
     * Realiza a criação de um agendamento
     *
     * @param request Requisição de criação de agendamento
     * @return Agendamento criado
     */
    public AppointmentInfoDTO create(AppointmentInfoDTO request) {
        return createAppointmentBusinessHandler.execute(request);
    }

    /**
     * Atualiza informações de um agendamento
     *
     * @param id      Identificador único do agendamento
     * @param request Informações de uma atualização de agendamento
     * @return Informações de um agendamento pós atualização
     */
    public AppointmentInfoDTO update(Long id, AppointmentInfoDTO request) {
        logger.info("Iniciando processo de atualização de agendamento");
        request.setId(id);
        return updateAppointmentBusinessHandler.execute(request);
    }

    /**
     * Deleta um agendamento
     *
     * @param id Identificador único do agendamento
     */
    public void delete(Long id) {
        deleteAppointmentBusinessHandler.execute(id);
    }
}
