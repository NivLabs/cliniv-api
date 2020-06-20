package br.com.nivlabs.gp.repository.custom.allergy;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.com.nivlabs.gp.controller.filters.AllergyFilters;
import br.com.nivlabs.gp.models.domain.Allergy;
import br.com.nivlabs.gp.models.domain.Allergy_;
import br.com.nivlabs.gp.models.dto.AllergyDTO;
import br.com.nivlabs.gp.repository.custom.CustomFilters;
import br.com.nivlabs.gp.repository.custom.GenericCustomRepository;
import br.com.nivlabs.gp.repository.custom.IExpression;
import br.com.nivlabs.gp.util.StringUtils;

public class AllergyRepositoryCustomImpl extends GenericCustomRepository<Allergy> implements AllergyRepositoryCustom {

    @Override
    public Page<AllergyDTO> resumedList(CustomFilters filters, Pageable pageSettings) {
        Page<Allergy> pageFromDatabase = pagination(createRestrictions(filters), pageSettings);

        List<AllergyDTO> listOfDTO = new ArrayList<>();

        pageFromDatabase.forEach(entity -> {
            AllergyDTO dtoConverted = new AllergyDTO();
            BeanUtils.copyProperties(entity, dtoConverted);
            listOfDTO.add(dtoConverted);
        });
        return new PageImpl<>(listOfDTO, pageSettings, pageFromDatabase.getTotalElements());
    }

    @Override
    protected List<IExpression<Allergy>> createRestrictions(CustomFilters customFilters) {
        AllergyFilters filters = (AllergyFilters) customFilters;

        List<IExpression<Allergy>> attributes = new ArrayList<>();

        if (!StringUtils.isNullOrEmpty(filters.getDescription())) {
            attributes.add((cb, from) -> cb.like(from.get(Allergy_.description), filters.getDescription()));
        }

        return attributes;
    }
}
