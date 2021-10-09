package br.com.nivlabs.gp.service.procedure.business;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.dto.ProcedureInfoDTO;

/**
 * 
 * Componente específico para atualização cadastral de procedimento
 *
 * @author viniciosarodrigues
 * @since 08-10-2021
 *
 */
@Component
public class UpdateProcedureBusinessHandler extends CreateOrUpdateProcedureBusinessHandler {

    /**
     * Atualiza informações cadastrais de um procedimento
     * 
     * @param procedureInfo Procedimento atualizado
     * @return Procedimento atualizado
     */
    @Transactional
    public ProcedureInfoDTO update(ProcedureInfoDTO procedureInfo) {
        if (procedureInfo.getId() == null) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "Informe o identificador do procedimento que será atualizado");
        }
        logger.info("Iniciando processo de atualização de procedimento :: {}", procedureInfo.getId());
        var procedureEntity = procedureRepository.findById(procedureInfo.getId()).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                String.format("Procedimento com código %s não encontrado!", procedureInfo.getId())));

        logger.info("Procedimento encontrado :: {}", procedureEntity.getDescription());
        parseProcedureInfoToEntity(procedureInfo, procedureEntity);

        procedureRepository.saveAndFlush(procedureEntity);

        logger.info("Atualização concluída com sucesso!");

        return procedureInfo;
    }

}
