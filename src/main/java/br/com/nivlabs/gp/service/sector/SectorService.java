package br.com.nivlabs.gp.service.sector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.controller.filters.SectorFilters;
import br.com.nivlabs.gp.models.dto.AccommodationDTO;
import br.com.nivlabs.gp.models.dto.SectorDTO;
import br.com.nivlabs.gp.models.dto.SectorInfoDTO;
import br.com.nivlabs.gp.service.BaseService;
import br.com.nivlabs.gp.service.sector.business.CreateAccomodationBusinessHandler;
import br.com.nivlabs.gp.service.sector.business.CreateSectorBusinessHandler;
import br.com.nivlabs.gp.service.sector.business.DeleteAccomodationBusinessHandler;
import br.com.nivlabs.gp.service.sector.business.SearchSectorBusinessHandler;
import br.com.nivlabs.gp.service.sector.business.UpdateAccomodationBusinessHandler;
import br.com.nivlabs.gp.service.sector.business.UpdateSectorBusinessHandler;

/**
 * 
 * Camada de serviço para processos com setores e acomodações
 *
 * @author viniciosarodrigues
 * @since 06-10-2021
 *
 */
@Service
public class SectorService implements BaseService {

    @Autowired
    private SearchSectorBusinessHandler searchSectorBusinessHandler;
    @Autowired
    private CreateSectorBusinessHandler createSectorBusinessHandler;
    @Autowired
    private UpdateSectorBusinessHandler updateSectorBusinessHandler;

    @Autowired
    private CreateAccomodationBusinessHandler createAccomodationBusinessHandler;
    @Autowired
    private UpdateAccomodationBusinessHandler updateAccomodationBusinessHandler;
    @Autowired
    private DeleteAccomodationBusinessHandler deleteAccomodationBusinessHandler;

    /**
     * Realiza a busca pagina de setores
     * 
     * @param filters Filtros de pesquisa
     * @param pageRequest Configurações de paginação
     * @return Página de setores
     */
    public Page<SectorDTO> getPage(SectorFilters filters, Pageable pageRequest) {
        return searchSectorBusinessHandler.getPage(filters, pageRequest);
    }

    /**
     * Busca informações do setor por identificador único do setor
     * 
     * @param id Identificador único do setor
     * @return Informações do setor
     */
    public SectorInfoDTO findInfoById(Long id) {
        return searchSectorBusinessHandler.byId(id);
    }

    /**
     * Atualiza informações de um setor
     * 
     * @param sectorInfo Novas informações do setor
     * @return Informações do setor atualizadas
     */
    public SectorInfoDTO update(long id, SectorInfoDTO sectorInfo) {
        sectorInfo.setId(id);
        return updateSectorBusinessHandler.update(sectorInfo);
    }

    /**
     * Cria um cadastro de um setor com as informações passadas no DTO
     * 
     * @param sectorInfo Informações do setor à ser criado
     * @return Informações do setor criado
     */
    public SectorInfoDTO persist(SectorInfoDTO sectorInfo) {
        return createSectorBusinessHandler.create(sectorInfo);
    }

    /**
     * Realiza a atualização cadastral de uma acomodação
     * 
     * @param id Identificador único da acomodação
     * @param request Novas informações da acomodação em questão à ser atualizada
     * @return Informações atualizadas da acomodação em questão
     */
    public AccommodationDTO updateAccommodation(Long id, AccommodationDTO request) {
        request.setId(id);
        return updateAccomodationBusinessHandler.update(request);
    }

    /**
     * Deleta acomodação por identificador único da acomodação
     * 
     * @param id Identificador único da acomodação
     */
    public void deletetAccomodationById(Long id) {
        deleteAccomodationBusinessHandler.byId(id);
    }

    /**
     * Realiza o cadastro de uma nova acomodação ao setor
     * 
     * @param request Informações da acomodação
     * @return Acomodação criada
     */
    public AccommodationDTO persist(AccommodationDTO request) {
        return createAccomodationBusinessHandler.create(request);
    }

}
