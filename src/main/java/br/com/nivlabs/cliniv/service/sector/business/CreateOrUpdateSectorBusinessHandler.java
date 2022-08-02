package br.com.nivlabs.cliniv.service.sector.business;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.nivlabs.cliniv.models.domain.Sector;
import br.com.nivlabs.cliniv.models.dto.SectorInfoDTO;
import br.com.nivlabs.cliniv.repository.SectorRepository;
import br.com.nivlabs.cliniv.service.BaseBusinessHandler;

/**
 * 
 * Componente base para criação de operações de atualização e criação de cadastro de setores
 *
 * @author viniciosarodrigues
 * @since 06-10-2021
 *
 */
@Component
public abstract class CreateOrUpdateSectorBusinessHandler implements BaseBusinessHandler {

    @Autowired
    protected Logger logger;

    @Autowired
    protected SectorRepository sectorRepository;

    /**
     * Converte um objeto de transferência em uma entidade relacional
     * 
     * @param sectorInfo DTO de informações do Setor
     * @param sectorEntity Entidade relacional de informações do Setor
     */
    protected void convertDTOToEntity(SectorInfoDTO sectorInfo, Sector sectorEntity) {
        sectorEntity.setId(sectorInfo.getId());
        sectorEntity.setDescription(sectorInfo.getDescription());
        if (sectorInfo.getCreatedAt() == null) {
            sectorInfo.setCreatedAt(LocalDateTime.now());
        }
        sectorEntity.setCreatedAt(sectorInfo.getCreatedAt());
    }

}
