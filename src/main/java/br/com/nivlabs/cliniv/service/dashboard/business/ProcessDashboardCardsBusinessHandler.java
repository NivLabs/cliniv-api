package br.com.nivlabs.cliniv.service.dashboard.business;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.cliniv.ApplicationMain;
import br.com.nivlabs.cliniv.controller.filters.AppointmentFilters;
import br.com.nivlabs.cliniv.enums.AppointmentStatus;
import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.Responsible;
import br.com.nivlabs.cliniv.models.domain.UserApplication;
import br.com.nivlabs.cliniv.models.dto.DashboardCardsInformationDTO;
import br.com.nivlabs.cliniv.repository.DashboardRepository;
import br.com.nivlabs.cliniv.repository.ResponsibleRepository;
import br.com.nivlabs.cliniv.repository.UserRepository;
import br.com.nivlabs.cliniv.service.BaseBusinessHandler;
import br.com.nivlabs.cliniv.service.appointment.AppointmentsService;
import br.com.nivlabs.cliniv.service.sticker.StickerService;
import br.com.nivlabs.cliniv.util.SecurityContextUtil;

/**
 * 
 * Componente base para manipulação dos cards do dashboard da aplicação
 *
 * @author viniciosarodrigues
 * @since 30-09-2021
 *
 */
@Component
public class ProcessDashboardCardsBusinessHandler implements BaseBusinessHandler {

    @Autowired
    protected Logger logger;

    @Autowired
    private DashboardRepository repo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ResponsibleRepository responsibleRepo;
    @Autowired
    private AppointmentsService appointmentsService;
    @Autowired
    private StickerService stickerService;

    /**
     * Busca informações detalhadas dos cards superiores
     * 
     * @return Informações dos cards do Dashboard
     */
    public DashboardCardsInformationDTO getCards() {

        var response = new DashboardCardsInformationDTO();
        logger.info("Iniciando setup das informações dos cards da unidade de atendimento...");
        setAttendanceUnitCards(response);
        final String userName = SecurityContextUtil.getAuthenticatedUser().getUsername();

        final UserApplication user = userRepo.findByUserName(userName)
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                        String.format("Usuário com o nome '%s' não encontrado", userName)));

        final Optional<Responsible> professional = responsibleRepo.findByCpf(user.getPerson().getCpf());

        if (professional.isPresent()) {
            logger.info("Iniciando setup das informações da próxima agenda do profissional (se for um profissional)...");
            setTodayAttendances(response, professional.get());
            logger.info("Iniciando setup das informações estatísticas do profissional (se for um profissional)...");
            setProfessionalStatistics(response, professional.get());
        } else {
            logger.info("O usuário logado não possui um profissional relacionado.");
        }
        logger.info("Iniciando setup dos lembretes do usuário...");
        setStickers(response);

        return response;
    }

    /**
     * Adiciona os lembretes do usuário, se houver
     * 
     * @param response Objeto de resposta do Dashboard
     */
    private void setStickers(DashboardCardsInformationDTO response) {
        response.getStickers().addAll(stickerService.getAll());
    }

    /**
     * Adiciona informações estatísticas do profissional, se houver
     * 
     * @param response Objeto de resposta do Dashboard
     * @param responsible profissional logado na sessão
     */
    private void setProfessionalStatistics(DashboardCardsInformationDTO response, Responsible responsible) {
        response.setTotalAttendanceProvided(repo
                .getProfessionalAttendanceProvidedCount(ZonedDateTime.now(ZoneId.of(ApplicationMain.AMERICA_SAO_PAULO)),
                                                        responsible.getId()));
        response.setTotalUnconfirmedAppointment(repo
                .getUnconfirmedAppointmentCountByResponsible(ZonedDateTime.now(ZoneId.of(ApplicationMain.AMERICA_SAO_PAULO)),
                                                             responsible.getId()));
        response.setTotalActiveAttendance(repo.getActiveAttendanceByProfessionalCount(responsible.getId()));
    }

    /**
     * Adiciona informação do próximo atendimento do profissional, se houver
     * 
     * @param response Objeto de resposta do Dashboard
     * @param responsible profissional logado na sessão
     */
    private void setTodayAttendances(DashboardCardsInformationDTO response, Responsible responsible) {
        AppointmentFilters filters = new AppointmentFilters();
        filters.setStartDate(LocalDate.now());
        filters.setEndDate(LocalDate.now());
        filters.setProfessionalId(responsible.getId().toString());
        filters.setStatus(AppointmentStatus.CONFIRMED.name());

        appointmentsService.findByFilters(filters).getContent().forEach(appointment -> response.getAppointments().add(appointment));
    }

    /**
     * Adiciona informações dos cards superiores do Dashboard, com informações estatísticas da unidade de atendimento
     * 
     * @param response Objeto de resposta do Dashboard
     */
    private void setAttendanceUnitCards(DashboardCardsInformationDTO response) {
        final long newPatients = repo.getNewPatientsCount(ZonedDateTime.now(ZoneId.of(ApplicationMain.AMERICA_SAO_PAULO)));
        final long medicalCareProvided = repo.getAttendanceProvidedCount(ZonedDateTime.now(ZoneId.of(ApplicationMain.AMERICA_SAO_PAULO)));
        final long canceled = repo.getCanceledAppointmentCount(ZonedDateTime.now(ZoneId.of(ApplicationMain.AMERICA_SAO_PAULO)));
        final long confirmed = repo.getConfirmedAppointmentCount(ZonedDateTime.now(ZoneId.of(ApplicationMain.AMERICA_SAO_PAULO)));

        response.setNewPatients(newPatients);
        response.setTotalActiveAttendance(medicalCareProvided);
        response.setCanceled(canceled);
        response.setConfirmed(confirmed);
    }

}
