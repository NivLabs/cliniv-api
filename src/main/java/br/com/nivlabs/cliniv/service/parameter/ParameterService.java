package br.com.nivlabs.cliniv.service.parameter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nivlabs.cliniv.models.dto.NewParameterValueDTO;
import br.com.nivlabs.cliniv.service.BaseService;
import br.com.nivlabs.cliniv.service.parameter.business.UpdateParameterBusinessHandler;

/**
 * Trata manipulação de parâmetros da aplicação
 * 
 * @author viniciosarodrigues
 *
 */
@Service
public class ParameterService implements BaseService {

    @Autowired
    private UpdateParameterBusinessHandler updateParameterBusinessHandler;

    /**
     * Atualiza valor de um determinado parâmetro
     * 
     * @param id Identificador único do parâmetro
     * @param newValue Requisição de mudança de valor de parâmetro
     */
    public void changeValueParameter(Long id, NewParameterValueDTO newValue) {
        updateParameterBusinessHandler.updateValue(id, newValue);
    }

}
