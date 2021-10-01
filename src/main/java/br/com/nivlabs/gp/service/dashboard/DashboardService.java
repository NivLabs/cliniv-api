package br.com.nivlabs.gp.service.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.models.dto.DashboardCardsInformationDTO;
import br.com.nivlabs.gp.service.BaseService;
import br.com.nivlabs.gp.service.dashboard.business.ProcessDashboardCardsBusinessHandler;

/**
 * Camada de serviços para processos com Dashboard
 * 
 *
 * @author viniciosarodrigues
 * @since 30-09-2021
 *
 */
@Service
public class DashboardService implements BaseService {

    @Autowired
    private ProcessDashboardCardsBusinessHandler processDashboardInformationsBusinessHandler;

    /**
     * Busca informações detalhadas dos cards superiores
     * 
     * @return Informações dos cards do Dashboard
     */
    public DashboardCardsInformationDTO getCardsInformations() {
        return processDashboardInformationsBusinessHandler.getCards();
    }
}
