package br.com.nivlabs.gp.service.responsible;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.controller.filters.ResponsibleFilters;
import br.com.nivlabs.gp.models.dto.ResponsibleDTO;
import br.com.nivlabs.gp.models.dto.ResponsibleInfoDTO;
import br.com.nivlabs.gp.service.BaseService;
import br.com.nivlabs.gp.service.responsible.business.CreateResponsibleBusinessHandler;
import br.com.nivlabs.gp.service.responsible.business.SearchResponsibleBusinessHandler;

/**
 * 
 * Camada de serviços para manipulação de profissionais e responsáveis
 *
 * @author viniciosarodrigues
 * @since 26-09-2021
 *
 */
@Service
public class ResponsibleService implements BaseService {

    @Autowired
    private SearchResponsibleBusinessHandler searchResponsibleBusinessHandler;
    @Autowired
    private UpdateResponsibleBusinessHandler updateResponsibleBusinessHandler;
    @Autowired
    private CreateResponsibleBusinessHandler createResponsibleBusinessHandler;

    /**
     * Realiza uma busca paginada por informações resumidas dos profissionais/responsáveis
     * 
     * @param filters Filtros de busca
     * @param pageRequest Configuraçõe de paginação
     * @return Página de responsáveis e profissionais
     */
    public Page<ResponsibleDTO> searchEntityPage(ResponsibleFilters filters, Pageable pageRequest) {
        return searchResponsibleBusinessHandler.getPage(filters, pageRequest);
    }

    /**
     * Busca um profissional ou responsável pelo identificador
     * 
     * @param id Identificador único do responsável / profissional
     * @return Responsável / Profissional encontrado
     */
    public ResponsibleInfoDTO findById(Long id) {
        return searchResponsibleBusinessHandler.byId(id);
    }

    /**
     * Busca um profissional ou responsável por CPF
     * 
     * @param cpf CPF do profissional
     * @return Informações do profissional
     */
    public ResponsibleInfoDTO findByCpf(String cpf) {
        return searchResponsibleBusinessHandler.byCPF(cpf);
    }

    /**
     * Atualiza informações do profissional / responsável
     * 
     * @param id Identificador único do profissional
     * @param responsible Informações atualizadas do profissional
     * @return Informações atualizadas do profissional
     */
    public ResponsibleInfoDTO update(Long id, ResponsibleInfoDTO request) {
        request.setId(id);
        return updateResponsibleBusinessHandler.update(request);
    }

    /**
     * Realiza cadastro de informações de profissional / responsável
     * 
     * @param request Informações para criação do profissional
     * @return Informações do profissional criado
     */
    public ResponsibleInfoDTO create(ResponsibleInfoDTO request) {
        request.setId(null);
        return createResponsibleBusinessHandler.create(request);
    }

}
