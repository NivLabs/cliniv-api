package br.com.nivlabs.gp.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.nivlabs.gp.models.dto.DataTransferObjectBase;

public interface CustomRepository<DTO extends DataTransferObjectBase> {
	public Page<DTO> resumedList(CustomFilters filters, Pageable pageSettings);
}
