package br.com.nivlabs.cliniv.repository.custom;

import org.springframework.data.domain.Page;

import br.com.nivlabs.cliniv.models.dto.DataTransferObjectBase;

public interface CustomRepository<E extends DataTransferObjectBase> {
    public Page<E> resumedList(CustomFilters filters);
}
