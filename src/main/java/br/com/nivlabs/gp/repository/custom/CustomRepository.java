package br.com.nivlabs.gp.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.nivlabs.gp.models.dto.DataTransferObjectBase;

public interface CustomRepository<E extends DataTransferObjectBase> {
    public Page<E> resumedList(CustomFilters filters, Pageable pageSettings);
}
