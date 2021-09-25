package br.com.nivlabs.gp.service.dynamicform.business;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.DynamicForm;
import br.com.nivlabs.gp.repository.DynamicFormRepository;
import br.com.nivlabs.gp.service.BaseBusinessHandler;

/**
 * 
 * Componente específico para exclusão de formulário dinâmico
 *
 * @author viniciosarodrigues
 * @since 24-09-2021
 *
 */
@Component
public class DeleteDynamicFormBusinessHandler implements BaseBusinessHandler {

    @Autowired
    private Logger logger;

    @Autowired
    private DynamicFormRepository dynamicFormRepo;

    /**
     * Realiza a exclusão física do formulário dinâmico
     * 
     * @param id Identificador único do formulário dinâmico
     */
    public void byId(Long id) {
        logger.info("Iniciando exclusão de formulário dinâmico :: {}", id);
        DynamicForm entity = dynamicFormRepo.findById(id).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                String.format("Formulários dinâmico com o identificador %s não encontrado.", id)));
        logger.info("Formulário encontrado :: {} | {} ", entity.getId(), entity.getTitle());
        dynamicFormRepo.delete(entity);
        logger.info("Formulário excluído com sucesso!");
    }

}
