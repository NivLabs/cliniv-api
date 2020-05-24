package br.com.nivlabs.gp.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.controller.filters.SectorFilters;
import br.com.nivlabs.gp.exception.ObjectNotFoundException;
import br.com.nivlabs.gp.models.domain.Sector;
import br.com.nivlabs.gp.models.dto.SectorDTO;
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

	public Page<SectorDTO> getPageWithFilter(SectorFilters filters, Pageable pageRequest) {
		return dao.resumedList(filters, pageRequest);
	}

	public SectorDTO findById(Long id) {
		Sector sector = dao.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException(String.format("Setor com ID: %s não encontrado", id)));
		SectorDTO sectorDTO = new SectorDTO();
		BeanUtils.copyProperties(sector, sectorDTO);
		return sectorDTO;
	}

	public SectorDTO update(Long id, SectorDTO sectorDTO) {
		Sector sector = dao.findById(id).orElseThrow(
				() -> new ObjectNotFoundException(String.format("Setor com o ID: [%s] não encontrado", id)));
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
		Sector auxEntity = dao.findById(id).orElseThrow(
				() -> new ObjectNotFoundException(String.format("Setor com o ID: [%s] não encontrado", id)));
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
