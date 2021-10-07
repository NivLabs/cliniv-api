package br.com.nivlabs.gp.service.sector.business;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.Sector;
import br.com.nivlabs.gp.models.dto.SectorInfoDTO;

/**
 * 
 * Componente específico para atualização de informações de um setor
 *
 * @author viniciosarodrigues
 * @since 06-10-2021
 *
 */
@Component
public class UpdateSectorBusinessHandler extends CreateOrUpdateSectorBusinessHandler {

    /**
     * Atualiza informações de um setor
     * 
     * @param sectorInfo Novas informações do setor
     * @return Informações do setor atualizadas
     */
    public SectorInfoDTO update(SectorInfoDTO sectorInfo) {
        if (sectorInfo.getId() == null) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "O identificador do setor é obrigatório para a atualização das informações!");
        }
        logger.info("Iniciando processo de atualização cadastral de setor :: ID: {} | Nome: {}", sectorInfo.getId(),
                    sectorInfo.getDescription());
        Sector sector = sectorRepository.findById(sectorInfo.getId()).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                String.format("Setor com o identificador %s não encontrado", sectorInfo.getId())));
        sectorInfo.setCreatedAt(sector.getCreatedAt());

        convertDTOToEntity(sectorInfo, sector);

        sectorRepository.save(sector);
        logger.info("Setor atualizado com sucesso!");

        return sectorInfo;
    }
}
