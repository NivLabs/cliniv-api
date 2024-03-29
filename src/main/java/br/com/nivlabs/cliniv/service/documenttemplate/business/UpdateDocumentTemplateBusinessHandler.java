package br.com.nivlabs.cliniv.service.documenttemplate.business;

import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.DocumentTemplate;
import br.com.nivlabs.cliniv.models.domain.DocumentTemplatePK;
import br.com.nivlabs.cliniv.models.dto.DocumentTemplateInfoDTO;
import br.com.nivlabs.cliniv.repository.DocumentTemplateRepository;
import br.com.nivlabs.cliniv.repository.UserRepository;
import br.com.nivlabs.cliniv.service.BaseBusinessHandler;
import br.com.nivlabs.cliniv.util.SecurityContextUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class UpdateDocumentTemplateBusinessHandler implements BaseBusinessHandler {

    @Autowired
    private Logger logger;
    @Autowired
    private DocumentTemplateRepository repo;
    @Autowired
    private UserRepository userRepository;

    public DocumentTemplateInfoDTO update(DocumentTemplateInfoDTO request) {
        logger.info("Iniciando processo de atualização de modelo de documento :: {}", request.getId());
        var userName = SecurityContextUtil.getAuthenticatedUser().getUsername();
        var user = userRepository.findByUserName(userName).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                "Nenhum usuário com o nome '" + userName + "' encontrado, o processo de busca de templates foi cancelado."));
        DocumentTemplate template = repo.findById(new DocumentTemplatePK(request.getId(), user.getId()))
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                        "Nenhum padrão de documento encontrado com o código " + request.getId() + " para o usuário " + userName));

        logger.info("Documento encontrado :: {} | {}... Atualizando informações...", template.getId().getId(),
                template.getId().getUserId());

        template.setDescription(request.getDescription());
        template.setText(request.getText());

        template = repo.save(template);
        logger.info("Modelo de documento atualizado com sucesso por {}!", userName);

        return request;
    }
}
