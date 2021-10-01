package br.com.nivlabs.gp.service.dashboard.business;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.models.dto.DashboardCardsInformationDTO;
import br.com.nivlabs.gp.repository.DashboardRepository;
import br.com.nivlabs.gp.service.BaseBusinessHandler;

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

    private static final String AMERICA_SAO_PAULO = "America/Sao_Paulo";
    @Autowired
    private DashboardRepository repo;

    /**
     * Busca informações detalhadas dos cards superiores
     * 
     * @return Informações dos cards do Dashboard
     */
    public DashboardCardsInformationDTO getCards() {
        var newPatients = repo.getNewPatientsCount(ZonedDateTime.now(ZoneId.of(AMERICA_SAO_PAULO)));
        var medicalCareProvided = repo.getMedicalCareProvidedCount(ZonedDateTime.now(ZoneId.of(AMERICA_SAO_PAULO)));
        var canceled = repo.getCanceledScheduleCount(ZonedDateTime.now(ZoneId.of(AMERICA_SAO_PAULO)));
        var confirmed = repo.getConfirmedScheduleCount(ZonedDateTime.now(ZoneId.of(AMERICA_SAO_PAULO)));
        var response = new DashboardCardsInformationDTO(newPatients, medicalCareProvided, canceled, confirmed);
        logger.info("Cards :: {}", response);
        return response;
    }

}
