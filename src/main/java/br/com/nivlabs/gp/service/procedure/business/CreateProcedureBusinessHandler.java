package br.com.nivlabs.gp.service.procedure.business;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.models.domain.tiss.Procedure;
import br.com.nivlabs.gp.models.dto.ProcedureInfoDTO;

/**
 * 
 * Componente específico para criação de um novo procedimento
 *
 * @author viniciosarodrigues
 * @since 08-10-2021
 *
 */
@Component
public class CreateProcedureBusinessHandler extends CreateOrUpdateProcedureBusinessHandler {

    /**
     * Cria um procedimento na aplicação
     * 
     * @param procedureInfo Informações do procedimento
     * @return Procedimento criado
     */
    @Transactional
    public ProcedureInfoDTO create(ProcedureInfoDTO procedureInfo) {
        procedureInfo.setId(null);

        logger.info("Iniciando a criação de um novo procedimento :: {}", procedureInfo);

        Procedure procedureEntity = new Procedure();

        parseProcedureInfoToEntity(procedureInfo, procedureEntity);

        procedureEntity = procedureRepository.saveAndFlush(procedureEntity);
        procedureInfo.setId(procedureEntity.getId());
        logger.info("Criação do novo procedimento realizada com sucesso :: {} | {}", procedureInfo.getId(), procedureInfo.getDescription());
        return procedureInfo;
    }

}
