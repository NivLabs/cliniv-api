package br.com.nivlabs.gp.service.documenttemplate.business;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.controller.filters.DocumentTemplateFilter;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.DocumentTemplate;
import br.com.nivlabs.gp.models.domain.DocumentTemplatePK;
import br.com.nivlabs.gp.models.domain.UserApplication;
import br.com.nivlabs.gp.models.dto.DocumentTemplateDTO;
import br.com.nivlabs.gp.models.dto.DocumentTemplateInfoDTO;
import br.com.nivlabs.gp.repository.DocumentTemplateRepository;
import br.com.nivlabs.gp.repository.UserRepository;
import br.com.nivlabs.gp.service.BaseBusinessHandler;
import br.com.nivlabs.gp.util.SecurityContextUtil;

@Component
public class SearchDocumentTemplateBusinessHandler implements BaseBusinessHandler {

    @Autowired
    private DocumentTemplateRepository documentTemplateRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * Busca informações de um template de documento por identificador único do documento
     * 
     * @param id Identificador único do template
     * @return Template de documento
     */
    public DocumentTemplateInfoDTO byId(Long id) {
        DocumentTemplate entity = documentTemplateRepository.findById(new DocumentTemplatePK(id, getContextUserInformations().getId()))
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND, "Modelo do documento com o identificador %s não foi encontrado"));

        return convertDocument(entity);
    }

    /**
     * Converte a entidade relacional em objeto de transferência
     * 
     * @param entity Entidade relacional
     * @return Objeto de transferência
     */
    private DocumentTemplateInfoDTO convertDocument(DocumentTemplate entity) {
        DocumentTemplateInfoDTO response = new DocumentTemplateInfoDTO();
        response.setId(entity.getPk().getId());
        response.setDescription(entity.getDescription());
        response.setText(entity.getText());
        return response;

    }

    /**
     * Realiza uma busca paginada de templates
     * 
     * @param filters Filtros de busca
     * @param pageSettings Configurações de páginas
     * @return Página de templates
     */
    @Transactional
    public Page<DocumentTemplateDTO> getPage(DocumentTemplateFilter filters, Pageable pageSettings) {
        filters.setUserId(getContextUserInformations().getId());
        return documentTemplateRepository.resumedList(filters, pageSettings);
    }

    /**
     * Busca informações detalhadas do usuário logado
     * 
     * @return Informações do usuário logado
     */
    private UserApplication getContextUserInformations() {
        var userName = SecurityContextUtil.getAuthenticatedUser().getUsername();
        var user = userRepository.findByUserName(userName).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                "Nenhum usuário com o nome '" + userName + "' encontrado, o processo de busca de modeslo de documento foi cancelado."));
        return user;
    }
}
