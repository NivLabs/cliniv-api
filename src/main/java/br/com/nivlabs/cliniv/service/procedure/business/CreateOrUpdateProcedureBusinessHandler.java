package br.com.nivlabs.cliniv.service.procedure.business;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.nivlabs.cliniv.models.domain.tiss.Procedure;
import br.com.nivlabs.cliniv.models.dto.ProcedureInfoDTO;
import br.com.nivlabs.cliniv.repository.ProcedureRepository;
import br.com.nivlabs.cliniv.service.BaseBusinessHandler;

/**
 * 
 * Componente base para criação de operações de criação e atualização de procedimentos
 *
 * @author viniciosarodrigues
 * @since 08-10-2021
 *
 */
@Component
public abstract class CreateOrUpdateProcedureBusinessHandler implements BaseBusinessHandler {

    @Autowired
    protected Logger logger;

    @Autowired
    protected ProcedureRepository procedureRepository;

    /**
     * Transfere informações de um DTO de procedimento para uma entidade de procedimento
     * 
     * @param procedureInfo DTO de procedimento
     * @param entity Entidade de procedimento
     */
    protected void parseProcedureInfoToEntity(ProcedureInfoDTO procedureInfo, Procedure procedureEntity) {
        procedureEntity.setId(procedureInfo.getId());
        procedureEntity.setDescription(procedureInfo.getDescription());
        procedureEntity.setBaseValue(procedureInfo.getBaseValue());
        procedureEntity.setFrequency(procedureInfo.getFrequency());
        procedureEntity.setSpecialAuthorization(procedureInfo.isSpecialAuthorization());
        procedureEntity.setPreviousAudit(procedureInfo.isPreviousAudit());
        procedureEntity.setSpecialty(procedureInfo.isSpecialty());
        procedureEntity.setMaxAge(procedureInfo.getMaxAge());
        procedureEntity.setMinAge(procedureInfo.getMinAge());
    }
}
