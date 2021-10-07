package br.com.nivlabs.gp.service.sector;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.controller.filters.SectorFilters;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.Accommodation;
import br.com.nivlabs.gp.models.domain.Accommodation_;
import br.com.nivlabs.gp.models.domain.Sector;
import br.com.nivlabs.gp.models.dto.AccommodationDTO;
import br.com.nivlabs.gp.models.dto.SectorDTO;
import br.com.nivlabs.gp.models.dto.SectorInfoDTO;
import br.com.nivlabs.gp.repository.AccommodationRepository;
import br.com.nivlabs.gp.service.BaseService;
import br.com.nivlabs.gp.service.sector.business.CreateSectorBusinessHandler;
import br.com.nivlabs.gp.service.sector.business.SearchSectorBusinessHandler;
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
    private AccommodationRepository roomOrBedRepository;

    @Autowired
    private SearchSectorBusinessHandler searchSectorBusinessHandler;
    @Autowired
    private CreateSectorBusinessHandler createSectorBusinessHandler;
    @Autowired
    private UpdateSectorBusinessHandler updateSectorBusinessHandler;

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
    public SectorInfoDTO update(Long id, SectorInfoDTO sectorInfo) {
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
     * Realiza a atualização de uma Sala ou Leito
     * 
     * @param id
     * @param request
     * @return
     */
    public AccommodationDTO updateAccomodation(Long id, AccommodationDTO request) {
        Accommodation entity = roomOrBedRepository.findById(id).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                String.format("Sala ou leito com o identificador %s não encontrado", id)));
        BeanUtils.copyProperties(request, entity, Accommodation_.ID);
        entity = roomOrBedRepository.save(entity);
        BeanUtils.copyProperties(entity, request);
        return request;
    }

    /**
     * Deleta uma sala ou leito
     * 
     * @param id
     */
    public void deleteRoomOrBed(Long id) {
        Accommodation entity = roomOrBedRepository.findById(id)
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND, String.format(
                                                                                         "Sala ou leito com o identificado %s não encontrado, não é possível deletar um registro inexistente.",
                                                                                         id)));
        roomOrBedRepository.delete(entity);
    }

    public AccommodationDTO persist(AccommodationDTO request) {
        Accommodation roomOrBad = new Accommodation();
        roomOrBad.setSector(new Sector(request.getSectorId()));
        roomOrBad.setDescription(request.getDescription());
        roomOrBad.setType(request.getType());
        roomOrBad = roomOrBedRepository.save(roomOrBad);

        request.setId(roomOrBad.getId());

        return request;
    }

}
