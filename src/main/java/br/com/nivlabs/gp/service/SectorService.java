package br.com.nivlabs.gp.service;

import java.time.LocalDateTime;

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
import br.com.nivlabs.gp.models.domain.Sector_;
import br.com.nivlabs.gp.models.dto.AccommodationDTO;
import br.com.nivlabs.gp.models.dto.SectorDTO;
import br.com.nivlabs.gp.models.dto.SectorInfoDTO;
import br.com.nivlabs.gp.repository.AccommodationRepository;
import br.com.nivlabs.gp.repository.SectorRepository;

/**
 * Classe SectorService.java
 * 
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 *
 * @since 13 Dez, 2019
 */
@Service
public class SectorService implements GenericService {

    @Autowired
    private SectorRepository dao;

    @Autowired
    private AccommodationRepository roomOrBedRepository;

    /**
     * Realiza a busca pagina de setores
     * 
     * @param filters
     * @param pageRequest
     * @return
     */
    public Page<SectorDTO> getPageWithFilter(SectorFilters filters, Pageable pageRequest) {
        return dao.resumedList(filters, pageRequest);
    }

    /**
     * Busca setor por id
     */
    public SectorInfoDTO findInfoById(Long id) {
        Sector sector = dao.findById(id).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                String.format("Setor com o identificador %s não encontrado", id)));
        SectorInfoDTO sectorInfoDTO = new SectorInfoDTO();
        BeanUtils.copyProperties(sector, sectorInfoDTO, Sector_.LIST_OF_ROOMS_OR_BEDS);
        sector.getListOfRoomsOrBeds().forEach(item -> sectorInfoDTO.getListOfRoomsOrBeds()
                .add(new AccommodationDTO(item.getId(), sector.getId(), item.getDescription(), item.getType())));
        return sectorInfoDTO;
    }

    /**
     * Atualiza informações do setor
     * 
     * @param id
     * @param sectorInfoDTO
     * @return
     */
    public SectorInfoDTO update(Long id, SectorInfoDTO sectorInfoDTO) {
        Sector sector = dao.findById(id).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                String.format("Setor com o identificador %s não encontrado", id)));
        BeanUtils.copyProperties(sectorInfoDTO, sector, Sector_.ID, Sector_.CREATED_AT, Sector_.LIST_OF_ROOMS_OR_BEDS);
        sector = dao.save(sector);
        BeanUtils.copyProperties(sector, sectorInfoDTO);
        return sectorInfoDTO;
    }

    /**
     * Realiza a atualização de uma Sala ou Leito
     * 
     * @param id
     * @param request
     * @return
     */
    public AccommodationDTO updateRoomOrBedDTO(Long id, AccommodationDTO request) {
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

    public void delete(SectorInfoDTO entity) {
        deleteById(entity.getId());
    }

    public void deleteById(Long id) {
        Sector auxEntity = dao.findById(id).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                String.format("Setor com o identificado %s não encontrado", id)));
        dao.delete(auxEntity);
    }

    public SectorInfoDTO persist(SectorInfoDTO sectorDTO) {
        Sector sector = new Sector();
        sector.setDescription(sectorDTO.getDescription());
        sector.setCreatedAt(LocalDateTime.now());
        sector = dao.save(sector);

        sectorDTO.setId(sector.getId());

        return sectorDTO;
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
