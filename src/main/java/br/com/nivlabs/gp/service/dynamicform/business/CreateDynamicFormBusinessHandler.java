package br.com.nivlabs.gp.service.dynamicform.business;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.models.domain.DynamicForm;
import br.com.nivlabs.gp.models.dto.DynamicFormDTO;
import br.com.nivlabs.gp.repository.DynamicFormRepository;
import br.com.nivlabs.gp.service.BaseBusinessHandler;

/**
 * 
 * Componente específico para criação de formulários dinâmicos
 *
 * @author viniciosarodrigues
 * @since 24-09-2021
 *
 */
@Component
public class CreateDynamicFormBusinessHandler implements BaseBusinessHandler {

    @Autowired
    private Logger logger;

    @Autowired
    private DynamicFormRepository dynamicFormRepo;

    /**
     * Cria um formulário dinâmico na aplicação
     * 
     * @param request Informações para criação de formulário dinâmico
     * @return Formulário dinâmico criado
     */
    public DynamicFormDTO create(DynamicFormDTO request) {
        logger.info("Inicianco processo de criação de formulário dinâmico :: {}", request.getTitle());
        DynamicForm entity = new DynamicForm();
        entity.setTitle(request.getTitle());
        entity = dynamicFormRepo.saveAndFlush(entity);
        request.setId(entity.getId());
        logger.info("Formulário criado com sucesso :: {} | {}", request.getId(), request.getTitle());
        return request;
    }

}
