package br.com.nivlabs.cliniv.service.sector.business;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.cliniv.controller.filters.SectorFilters;
import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.Sector;
import br.com.nivlabs.cliniv.models.dto.AccommodationDTO;
import br.com.nivlabs.cliniv.models.dto.SectorDTO;
import br.com.nivlabs.cliniv.models.dto.SectorInfoDTO;
import br.com.nivlabs.cliniv.repository.SectorRepository;
import br.com.nivlabs.cliniv.service.BaseBusinessHandler;

/**
 * 
 * Componente específico para busca de setores e acomodações
 *
 * @author viniciosarodrigues
 * @since 06-10-2021
 *
 */
@Component
public class SearchSectorBusinessHandler implements BaseBusinessHandler {

    @Autowired
    protected Logger logger;

    @Autowired
    protected SectorRepository sectorRepository;

    /**
     * Realiza a busca pagina de setores
     * 
     * @param filters Filtros de pesquisa
     * @param pageRequest Configurações de paginação
     * @return Página de setores
     */
    public Page<SectorDTO> getPage(SectorFilters filters, Pageable pageRequest) {
        logger.info("Iniciando busca paginada de setores :: {}", filters);
        return sectorRepository.resumedList(filters, pageRequest);
    }

    /**
     * Busca informações do setor por identificador único do setor
     * 
     * @param id Identificador único do setor
     * @return Informações do setor
     */
    @Transactional
    public SectorInfoDTO byId(Long id) {
        if (id == null) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "Informe o identificador único do setor para a consulta.");
        }
        Sector sectorEntity = sectorRepository.findById(id).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                String.format("Setor com o identificador %s não encontrado", id)));

        return convertEntityToDTO(sectorEntity);
    }

    /**
     * Converte entidade relacional de Setor para DTO de Setor
     * 
     * @param sectorEntity Entidade relacional de setor
     * @return DTO de setor
     */
    @Transactional
    private SectorInfoDTO convertEntityToDTO(Sector sectorEntity) {
        logger.info("Convertendo entidade relacional de Setor para DTO");
        SectorInfoDTO sectorInfo = new SectorInfoDTO();
        sectorInfo.setId(sectorEntity.getId());
        sectorInfo.setDescription(sectorEntity.getDescription());
        sectorInfo.setCreatedAt(sectorEntity.getCreatedAt());

        sectorEntity.getListOfRoomsOrBeds().forEach(item -> sectorInfo.getListOfRoomsOrBeds()
                .add(new AccommodationDTO(item.getId(), sectorEntity.getId(), item.getDescription(), item.getType())));

        logger.info("Setor encontrado :: {}", sectorInfo);
        return sectorInfo;
    }
}
