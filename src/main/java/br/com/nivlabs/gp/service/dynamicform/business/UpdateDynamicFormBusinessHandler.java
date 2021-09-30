package br.com.nivlabs.gp.service.dynamicform.business;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.DynamicForm;
import br.com.nivlabs.gp.models.dto.DynamicFormDTO;
import br.com.nivlabs.gp.repository.DynamicFormRepository;
import br.com.nivlabs.gp.service.BaseBusinessHandler;

/**
 * 
 * Componente específico para atualização de formulário dinâmico
 *
 * @author viniciosarodrigues
 * @since 24-09-2021
 *
 */
@Component
public class UpdateDynamicFormBusinessHandler implements BaseBusinessHandler {

    @Autowired
    private Logger logger;

    @Autowired
    private DynamicFormRepository dynamicFormRepo;

    /**
     * Atualiza informações do formulário dinâmico
     * 
     * @param id Identificador único do formulário
     * @param request Informações a serem atualizadas
     * @return Formulário atualizado
     */
    public DynamicFormDTO update(DynamicFormDTO request) {
        logger.info("Inicianco processo de atualização de formulário dinâmico :: {} | {} ", request.getId(), request.getTitle());
        DynamicForm entity = dynamicFormRepo.findById(request.getId()).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                String.format("Formulário dinâmico com o identificador %s não encontrado.", request.getId())));

        entity.setTitle(request.getTitle());

        dynamicFormRepo.saveAndFlush(entity);

        logger.info("Formulário atualizado com sucesso :: {} | {}", request.getId(), request.getTitle());

        return request;
    }

}
