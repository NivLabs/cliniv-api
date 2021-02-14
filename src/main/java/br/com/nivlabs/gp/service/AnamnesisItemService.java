/**
 * 
 */
package br.com.nivlabs.gp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.DynamicFormQuestion;
import br.com.nivlabs.gp.models.domain.DynamicFormQuestion_;
import br.com.nivlabs.gp.models.dto.DynamicFormQuestionDTO;
import br.com.nivlabs.gp.repository.AnamnesisItemRepository;

/**
 * AnamnesisItem.java
 *
 * @author <a href="henriquedreyer@gmail.com">Henrique Dreyer</a>
 *
 * @since 12 de set de 2019
 * 
 */
@Service
public class AnamnesisItemService implements GenericService {

    @Autowired
    private AnamnesisItemRepository dao;

    public Page<DynamicFormQuestionDTO> searchDTOPage(Pageable pageRequest) {
        Page<DynamicFormQuestion> pageFromDb = dao.findAll(pageRequest);
        List<DynamicFormQuestionDTO> responseList = new ArrayList<>();
        pageFromDb.getContent().stream().map(DynamicFormQuestion::getAnamnesisItemDTOFromDomain).forEach(responseList::add);
        return new PageImpl<>(responseList, pageRequest, pageFromDb.getTotalElements());
    }

    public DynamicFormQuestion findById(Long id) {
        return dao.findById(id).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                String.format("Questão com o identificador %s não encontrada.", id)));
    }

    public DynamicFormQuestion update(Long id, DynamicFormQuestion entity) {
        DynamicFormQuestion anamnesisItem = findById(id);
        BeanUtils.copyProperties(entity, anamnesisItem, DynamicFormQuestion_.ID);
        return dao.save(anamnesisItem);
    }

    public DynamicFormQuestion persist(DynamicFormQuestion entity) {
        entity.setId(null);
        return dao.save(entity);
    }

}
