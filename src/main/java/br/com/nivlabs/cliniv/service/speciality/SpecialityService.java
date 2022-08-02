package br.com.nivlabs.cliniv.service.speciality;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.nivlabs.cliniv.controller.filters.SpecialityFilter;
import br.com.nivlabs.cliniv.models.dto.SpecialityDTO;
import br.com.nivlabs.cliniv.models.dto.SpecialityInfoDTO;
import br.com.nivlabs.cliniv.service.speciality.business.CreateSpecialityBusinessHandler;
import br.com.nivlabs.cliniv.service.speciality.business.SearchSpecialityBusinessHandler;
import br.com.nivlabs.cliniv.service.speciality.business.UpdateSpecialityBusinessHandler;

/**
 * 
 * Camada de serviços para operações com Especialidades
 *
 * @author viniciosarodrigues
 * @since 07-10-2021
 *
 */
@Service
public class SpecialityService {

    @Autowired
    private SearchSpecialityBusinessHandler searchSpecialityBusinessHander;
    @Autowired
    private CreateSpecialityBusinessHandler createSpecialityBusinessHandler;
    @Autowired
    private UpdateSpecialityBusinessHandler updateSpecialityBusinessHandler;

    /**
     * Busca infomações paginadas de especialidades cadastradas no sistema
     * 
     * @param filter Filtros de busca
     * @param pageSettings Configurações de paginação
     * @return Informações paginadas de especialidades cadastradas no sistema
     */
    public Page<SpecialityDTO> getPage(SpecialityFilter filter, Pageable pageSettings) {
        return searchSpecialityBusinessHander.getPage(filter, pageSettings);
    }

    /**
     * Busca informações detalhadas de uma especialidade baseada no identificador único da especialidade
     * 
     * @param id Identificador único da especialidade
     * @return Informações detalhadas da especialidade
     */
    public SpecialityInfoDTO findById(Long id) {
        return searchSpecialityBusinessHander.byId(id);
    }

    /**
     * Cria um novo cadastro de especialidade
     * 
     * @param specialityInfo Informações da especialidade à ser criada
     * @return Especialidade criada
     */
    public SpecialityInfoDTO persist(SpecialityInfoDTO specialityInfo) {
        return createSpecialityBusinessHandler.create(specialityInfo);
    }

    /**
     * Atualiza informações de uma especialidade
     * 
     * @param specialityInfo Informações da especialidade
     * @return Especialidade atualizada
     */
    public SpecialityInfoDTO update(Long id, SpecialityInfoDTO specialityInfo) {
        specialityInfo.setId(id);
        return updateSpecialityBusinessHandler.update(specialityInfo);
    }
}
