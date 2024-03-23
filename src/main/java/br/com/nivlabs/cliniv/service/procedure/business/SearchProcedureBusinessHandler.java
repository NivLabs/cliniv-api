package br.com.nivlabs.cliniv.service.procedure.business;

import br.com.nivlabs.cliniv.controller.filters.ProcedureFilters;
import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.tiss.Procedure;
import br.com.nivlabs.cliniv.models.dto.ProcedureDTO;
import br.com.nivlabs.cliniv.models.dto.ProcedureInfoDTO;
import br.com.nivlabs.cliniv.repository.ProcedureRepository;
import br.com.nivlabs.cliniv.service.BaseBusinessHandler;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * Componente específico para consulta de procedimentos
 *
 * @author viniciosarodrigues
 * @since 08-10-2021
 */
@Component
public class SearchProcedureBusinessHandler implements BaseBusinessHandler {

    @Autowired
    protected Logger logger;

    @Autowired
    protected ProcedureRepository procedureRepository;

    /**
     * Realiza uma busca paginada de procedimentos
     *
     * @param filters     Filtros de busca de procedimentos
     * @param pageRequest Configurações de paginação
     * @return Página de procedimentos
     */
    @Transactional
    public Page<ProcedureDTO> getPage(ProcedureFilters filters) {
        return procedureRepository.resumedList(filters);
    }

    /**
     * Realiza uma busca de procedimento por identificador único do mesmo
     *
     * @param id Identificador único do procedimento
     * @return Procedimento encontrado
     */
    @Transactional
    public ProcedureInfoDTO byId(Long id) {
        if (id == null) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "O identificador do procedimento não foi enviado para a pesquisa");
        }
        logger.info("Iniciando busca de procedimento por ID :: {}", id);
        var procedureEntity = procedureRepository.findById(id).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                String.format("Procedimento com código %s não encontrado!", id)));

        return convertEntityToDTO(procedureEntity);
    }

    /**
     * Converte uma entidade relacional em DTO
     *
     * @param procedureEntity Entidade relacional que representa um procedimento
     * @return DTO de procedimento
     */
    @Transactional
    ProcedureInfoDTO convertEntityToDTO(Procedure procedureEntity) {
        logger.info("Convertendo entidade encontrada de Procedimento em DTO...");

        ProcedureInfoDTO procedureInfo = new ProcedureInfoDTO();

        procedureInfo.setId(procedureEntity.getId());
        procedureInfo.setDescription(procedureEntity.getDescription());
        procedureInfo.setBaseValue(procedureEntity.getBaseValue());
        procedureInfo.setFrequency(procedureEntity.getFrequency());
        procedureInfo.setSpecialAuthorization(procedureEntity.isSpecialAuthorization());
        procedureInfo.setPreviousAudit(procedureEntity.isPreviousAudit());
        procedureInfo.setSpecialty(procedureEntity.isSpecialty());
        procedureInfo.setMaxAge(procedureEntity.getMaxAge());
        procedureInfo.setMinAge(procedureEntity.getMinAge());

        logger.info("Procedimento convertido :: {}", procedureInfo);

        return procedureInfo;
    }

}
