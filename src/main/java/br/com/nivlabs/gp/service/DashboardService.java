package br.com.nivlabs.gp.service;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.models.dto.DashboardInfoDTO;
import br.com.nivlabs.gp.repository.DashboardRepository;

@Service
public class DashboardService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String AMERICA_SAO_PAULO = "America/Sao_Paulo";
    @Autowired
    private DashboardRepository repo;

    /**
     * Busca informações detalhadas dos cards superiores
     * 
     * @return Informações do Dashboard
     */
    public DashboardInfoDTO getInfo() {
        var newPatients = repo.getNewPatientsCount(ZonedDateTime.now(ZoneId.of(AMERICA_SAO_PAULO)));
        var medicalCareProvided = repo.getMedicalCareProvidedCount(ZonedDateTime.now(ZoneId.of(AMERICA_SAO_PAULO)));
        var canceled = repo.getCanceledScheduleCount(ZonedDateTime.now(ZoneId.of(AMERICA_SAO_PAULO)));
        var confirmed = repo.getConfirmedScheduleCount(ZonedDateTime.now(ZoneId.of(AMERICA_SAO_PAULO)));
        var response = new DashboardInfoDTO(newPatients, medicalCareProvided, canceled, confirmed);
        logger.info("Dashboard :: {}", response);
        return response;
    }
}
