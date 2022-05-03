package br.com.nivlabs.gp.service.documenttemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.controller.filters.DocumentTemplateFilter;
import br.com.nivlabs.gp.models.dto.DocumentTemplateDTO;
import br.com.nivlabs.gp.models.dto.DocumentTemplateInfoDTO;
import br.com.nivlabs.gp.service.BaseService;
import br.com.nivlabs.gp.service.documenttemplate.business.CreateDocumentTemplateBusinessHandler;
import br.com.nivlabs.gp.service.documenttemplate.business.SearchDocumentTemplateBusinessHandler;

@Service
public class DocumentTemplateService implements BaseService {

    @Autowired
    private SearchDocumentTemplateBusinessHandler searchDocumentTemplateBusinessHandler;

    @Autowired
    private CreateDocumentTemplateBusinessHandler createDocumentTemplateBusinessHandler;

    /**
     * Realiza a busca paginada de templates de documentos
     * 
     * @param filters Filtros de pesquisa
     * @param pageSettings Configurações de paginação
     * @return Página de templates
     */
    public Page<DocumentTemplateDTO> getPage(DocumentTemplateFilter filters, Pageable pageSettings) {
        return searchDocumentTemplateBusinessHandler.getPage(filters, pageSettings);
    }

    /**
     * Realiza a busca de um template de documento baseado no identificador único do mesmo
     * 
     * @param id Identificador único do template
     * @return Informações de um template de documento
     */
    public DocumentTemplateInfoDTO findById(Long id) {
        return searchDocumentTemplateBusinessHandler.byId(id);
    }

    /**
     * Realiza a criação de um template de documento para o usuário logado
     * 
     * @param request Requisição de criação de template de documento
     * @return Informações de um template de documento
     */
    public DocumentTemplateInfoDTO create(DocumentTemplateInfoDTO request) {
        return createDocumentTemplateBusinessHandler.create(request);
    }
}
