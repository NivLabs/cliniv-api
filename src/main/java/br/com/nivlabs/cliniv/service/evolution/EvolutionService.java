package br.com.nivlabs.cliniv.service.evolution;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nivlabs.cliniv.models.dto.EvolutionInfoDTO;
import br.com.nivlabs.cliniv.service.BaseService;
import br.com.nivlabs.cliniv.service.evolution.business.CreateEvolutionBusinessHandler;

/**
 * Camada de serviço para manipulação de evoluções clínicas
 * 
 * @author viniciosarodrigues
 *
 */
@Service
public class EvolutionService implements BaseService {

    @Autowired
    CreateEvolutionBusinessHandler createEvolutionBusinessHandler;

    /**
     * Insere uma nova evolução clínica do paciente
     * 
     * @param request Requisição de criação de evolução clínica do paciente
     * @return Informações da evolução criada
     */
    public EvolutionInfoDTO createNewEvolution(EvolutionInfoDTO request) {
        return createEvolutionBusinessHandler.create(request);
    }

}
