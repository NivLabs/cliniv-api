package br.com.ft.gdp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ft.gdp.exception.ObjectNotFoundException;
import br.com.ft.gdp.models.domain.Sector;
import br.com.ft.gdp.models.dto.SectorDTO;
import br.com.ft.gdp.repository.SectorRepository;

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

    public List<SectorDTO> getSectorsGroupedBySuper() {

        List<Sector> pageOfSector = dao.findBySuperSectorIsNull();

        List<SectorDTO> listOfSectorDTO = new ArrayList<>();

        pageOfSector.forEach(sector -> {
            SectorDTO sectorConverted = new SectorDTO();
            BeanUtils.copyProperties(sector, sectorConverted);
            listOfSectorDTO.add(sectorConverted);
        });
        return listOfSectorDTO;
    }

    public SectorDTO findById(Long id) {
        Sector sector = dao.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Setor com ID: %s não encontrado", id)));
        SectorDTO sectorDTO = new SectorDTO();
        BeanUtils.copyProperties(sector, sectorDTO, "id");
        return sectorDTO;
    }

    public SectorDTO update(Long id, SectorDTO sectorDTO) {
        Sector sector = dao.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                String.format("Setor com o ID: [%s] não encontrado", id)));
        BeanUtils.copyProperties(sectorDTO, sector, "id");
        sector = dao.save(sector);
        BeanUtils.copyProperties(sector, sectorDTO, "id");
        return sectorDTO;
    }

    @Override
    public void delete(SectorDTO entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteById(Long id) {
        Sector auxEntity = dao.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                String.format("Setor com o ID: [%s] não encontrado", id)));
        dao.delete(auxEntity);
    }

    @Override
    public SectorDTO persist(SectorDTO sectorDTO) {
        Sector sector = new Sector();
        sector.setDescription(sectorDTO.getDescription());
        sector = dao.save(sector);

        sectorDTO.setId(sector.getId());

        return sectorDTO;
    }

}
