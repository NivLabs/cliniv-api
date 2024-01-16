package br.com.nivlabs.cliniv.service.sector.business;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Component;

import br.com.nivlabs.cliniv.models.domain.Sector;
import br.com.nivlabs.cliniv.models.dto.SectorInfoDTO;

/**
 * 
 * Componente específico para criação de cadastro de setores
 *
 * @author viniciosarodrigues
 * @since 06-10-2021
 *
 */
@Component
public class CreateSectorBusinessHandler extends CreateOrUpdateSectorBusinessHandler {

    /**
     * Cria um cadastro de um setor com as informações passadas no DTO
     * 
     * @param sectorInfo Informações do setor à ser criado
     * @return Informações do setor criado
     */
    @Transactional
    public SectorInfoDTO create(SectorInfoDTO sectorInfo) {
        logger.info("Iniciando processo de criação de cadastro de um novo setor de atendimento....");
        Sector sector = new Sector();

        sectorInfo.setId(null);
        sectorInfo.setCreatedAt(null);

        convertDTOToEntity(sectorInfo, sector);

        sector = sectorRepository.save(sector);
        sectorInfo.setId(sector.getId());
        logger.info("Setor criado :: ID: {} | Nome: {}", sectorInfo.getId(), sectorInfo.getDescription());
        return sectorInfo;
    }
}
