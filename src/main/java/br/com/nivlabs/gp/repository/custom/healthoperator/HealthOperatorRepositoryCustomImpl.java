package br.com.nivlabs.gp.repository.custom.healthoperator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import br.com.nivlabs.gp.controller.filters.HealthOperatorFilters;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.HealthOperator;
import br.com.nivlabs.gp.models.domain.HealthOperator_;
import br.com.nivlabs.gp.models.dto.HealthOperatorDTO;
import br.com.nivlabs.gp.repository.custom.CustomFilters;
import br.com.nivlabs.gp.repository.custom.GenericCustomRepository;
import br.com.nivlabs.gp.repository.custom.IExpression;
import br.com.nivlabs.gp.util.StringUtils;

/**
 * Repositório customizado para operadoras de saúde
 * 
 * @author viniciosarodrigues
 *
 */
public class HealthOperatorRepositoryCustomImpl extends GenericCustomRepository<HealthOperator> implements HealthOperatorRepositoryCustom {

    @Override
    public Page<HealthOperatorDTO> resumedList(CustomFilters filters, Pageable pageSettings) {
        Page<HealthOperator> pageFromDatabase = pagination(createRestrictions(filters), pageSettings);

        List<HealthOperatorDTO> listOfDTO = new ArrayList<>();

        pageFromDatabase.forEach(entity -> {
            HealthOperatorDTO dtoConverted = new HealthOperatorDTO();
            BeanUtils.copyProperties(entity, dtoConverted);
            listOfDTO.add(dtoConverted);
        });
        return new PageImpl<>(listOfDTO, pageSettings, pageFromDatabase.getTotalElements());
    }

    @Override
    protected List<IExpression<HealthOperator>> createRestrictions(CustomFilters customFilters) {
        if (!(customFilters instanceof HealthOperatorFilters)) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "Tipo de filtro inválido para Operadoras de saúde!");
        }
        HealthOperatorFilters filters = (HealthOperatorFilters) customFilters;

        List<IExpression<HealthOperator>> attributes = new ArrayList<>();

        if (!StringUtils.isNullOrEmpty(filters.getId()) && StringUtils.isNumeric(filters.getId())) {
            attributes.add((cb, from) -> cb.equal(from.get(HealthOperator_.id), Long.parseLong(filters.getId())));
        }
        if (!StringUtils.isNullOrEmpty(filters.getAnsCode())) {
            attributes.add((cb, from) -> cb.equal(from.get(HealthOperator_.ansCode), Long.parseLong(filters.getAnsCode())));
        }
        if (!StringUtils.isNullOrEmpty(filters.getCnpj())) {
            attributes.add((cb, from) -> cb.equal(from.get(HealthOperator_.cnpj), filters.getCnpj()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getCompanyName())) {
            attributes.add((cb, from) -> cb.equal(from.get(HealthOperator_.companyName), filters.getCompanyName()));
        }
        if (!StringUtils.isNullOrEmpty(filters.getFantasyName())) {
            attributes.add((cb, from) -> cb.equal(from.get(HealthOperator_.fantasyName), filters.getFantasyName()));
        }
        if (filters.getModality() != null) {
            attributes.add((cb, from) -> cb.equal(from.get(HealthOperator_.modality), filters.getModality()));
        }
        return attributes;
    }

}
