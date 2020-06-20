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
import br.com.nivlabs.gp.models.domain.RoomOrBed;
import br.com.nivlabs.gp.models.domain.Sector;
import br.com.nivlabs.gp.models.dto.RoomOrBedDTO;
import br.com.nivlabs.gp.models.dto.SectorDTO;
import br.com.nivlabs.gp.models.dto.SectorInfoDTO;
import br.com.nivlabs.gp.repository.RoomOrBedRepository;
import br.com.nivlabs.gp.repository.SectorRepository;

/**
 * Classe SectorService.java
 * 
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 *
 * @since 13 Dez, 2019
 */
@Service
public class SectorService implements GenericService<SectorDTO, Long> {

    @Autowired
    private SectorRepository dao;

    @Autowired
    private RoomOrBedRepository roomOrBedRepository;

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
        BeanUtils.copyProperties(sector, sectorInfoDTO, "listOfRoomsOrBeds");
        sector.getListOfRoomsOrBeds().forEach(item -> sectorInfoDTO.getListOfRoomsOrBeds()
                .add(new RoomOrBedDTO(item.getId(), sector.getId(), item.getDescription(), item.getType())));
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
        BeanUtils.copyProperties(sectorInfoDTO, sector, "id", "createdAt");
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
    public RoomOrBedDTO updateRoomOrBedDTO(Long id, RoomOrBedDTO request) {
        RoomOrBed entity = roomOrBedRepository.findById(id).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                String.format("Sala ou leito com o identificador %s não encontrado", id)));
        BeanUtils.copyProperties(request, entity, "id");
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
        RoomOrBed entity = roomOrBedRepository.findById(id)
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND, String.format(
                                                                                         "Sala ou leito com o identificado %s não encontrado, não é possível deletar um registro inexistente.",
                                                                                         id)));
        roomOrBedRepository.delete(entity);
    }

    @Override
    public void delete(SectorDTO entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteById(Long id) {
        Sector auxEntity = dao.findById(id).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                String.format("Setor com o identificado %s não encontrado", id)));
        dao.delete(auxEntity);
    }

    @Override
    public SectorDTO persist(SectorDTO sectorDTO) {
        Sector sector = new Sector();
        sector.setDescription(sectorDTO.getDescription());
        sector.setCreatedAt(LocalDateTime.now());
        sector = dao.save(sector);

        sectorDTO.setId(sector.getId());

        return sectorDTO;
    }

    public RoomOrBedDTO persist(RoomOrBedDTO request) {
        RoomOrBed roomOrBad = new RoomOrBed();
        roomOrBad.setSector(new Sector(request.getSectorId()));
        roomOrBad.setDescription(request.getDescription());
        roomOrBad.setType(request.getType());
        roomOrBad = roomOrBedRepository.save(roomOrBad);

        request.setId(roomOrBad.getId());

        return request;
    }

}
