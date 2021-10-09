package br.com.nivlabs.gp.service.procedure.business;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.repository.ProcedureRepository;
import br.com.nivlabs.gp.service.BaseBusinessHandler;

/**
 * 
 * Componente específico para exclusão de procedimento no sistema
 *
 * @author viniciosarodrigues
 * @since 08-10-2021
 *
 */
@Component
public class DeleteProcedureBusinessHandler implements BaseBusinessHandler {

    @Autowired
    protected Logger logger;
    @Autowired
    protected ProcedureRepository procedureRepository;

    /**
     * Exclui um procedimento da aplicação
     * 
     * @param id Identificador único do procedimento
     */
    @Transactional
    public void byId(Long id) {
        logger.info("Iniciando processo de exclusão de procedimento por identificador :: {}", id);
        var procedureEntity = procedureRepository.findById(id).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                String.format("Procedimento com código %s não encontrado!", id)));
        logger.info("Procedimento que será excluído :: {}", procedureEntity.getDescription());
        procedureRepository.delete(procedureEntity);
        logger.info("Procedimento excluído com sucesso!");
    }

}
