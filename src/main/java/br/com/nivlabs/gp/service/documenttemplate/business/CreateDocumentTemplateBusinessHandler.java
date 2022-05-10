package br.com.nivlabs.gp.service.documenttemplate.business;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.DocumentTemplate;
import br.com.nivlabs.gp.models.domain.DocumentTemplatePK;
import br.com.nivlabs.gp.models.dto.DocumentTemplateInfoDTO;
import br.com.nivlabs.gp.repository.DocumentTemplateRepository;
import br.com.nivlabs.gp.repository.UserRepository;
import br.com.nivlabs.gp.service.BaseBusinessHandler;
import br.com.nivlabs.gp.util.SecurityContextUtil;

@Component
public class CreateDocumentTemplateBusinessHandler implements BaseBusinessHandler {

    @Autowired
    private Logger logger;
    @Autowired
    private DocumentTemplateRepository dao;
    @Autowired
    private UserRepository userRepository;

    /**
     * Cria um template de documento digital na aplicação
     * 
     * @param request Objeto de requisição para criação de template de documento digital
     * @return Template de documento criado na base
     */
    public DocumentTemplateInfoDTO create(DocumentTemplateInfoDTO request) {
        var userName = SecurityContextUtil.getAuthenticatedUser().getUsername();
        var user = userRepository.findByUserName(userName).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                "Nenhum usuário com o nome '" + userName + "' encontrado, o processo de busca de templates foi cancelado."));
        logger.info("Iniciando processo de criação de modelo de documento para o usuário :: {}", userName);
        DocumentTemplate template = new DocumentTemplate();
        template.setPk(new DocumentTemplatePK(null, user.getId()));
        template.setDescription(request.getDescription());
        template.setText(request.getText());

        template = dao.save(template);

        logger.info("Modelo de documento criado com sucesso por {}!", userName);
        return convertDocument(template);
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
}
