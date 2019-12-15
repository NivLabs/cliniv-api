package br.com.ft.gdp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

    public Page<SectorDTO> searchEntityPage(Pageable pageRequest) {
       
    	  Page<Sector> pageOfSector = dao.findAll(pageRequest);

          List<SectorDTO> listOfSectorDTO = new ArrayList<>();

          pageOfSector.forEach(sector -> {
        	  SectorDTO sectorConverted = new SectorDTO();
              sectorConverted = sector.getSectorDTOFromDomain();
              BeanUtils.copyProperties(sector, sectorConverted, "id");
              listOfSectorDTO.add(sectorConverted);
          });
          return new PageImpl<>(listOfSectorDTO, pageRequest, pageOfSector.getTotalElements());
    }

    
    public SectorDTO findById(Long id) {
    	  Sector Sector = dao.findById(id)
                  .orElseThrow(() -> new ObjectNotFoundException(String.format("Setor com ID: [%s] não encontrado", id)));

          return Sector.getSectorDTOFromDomain();
    }

    public SectorDTO update(Long id, SectorDTO sectorDTO) {
    	Sector auxEntity = dao.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                String.format("Setor com o ID: [%s] não encontrado", id)));
        BeanUtils.copyProperties(sectorDTO, auxEntity, "id");
        return dao.save(auxEntity).getSectorDTOFromDomain();
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

	public SectorDTO persist(SectorDTO newSector) {
		newSector.setId(null);
		return dao.save(newSector.getSectorDomainFromDTO()).getSectorDTOFromDomain();
	}

}
