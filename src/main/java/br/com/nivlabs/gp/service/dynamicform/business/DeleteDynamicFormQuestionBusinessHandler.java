package br.com.nivlabs.gp.service.dynamicform.business;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.DynamicFormQuestion;
import br.com.nivlabs.gp.repository.DynamicFormQuestionRepository;
import br.com.nivlabs.gp.service.BaseBusinessHandler;

/**
 * 
 * Componente específico para exclusão de questões de um formulário dinâmico
 *
 * @author viniciosarodrigues
 * @since 24-09-2021
 *
 */
@Component
public class DeleteDynamicFormQuestionBusinessHandler implements BaseBusinessHandler {

    @Autowired
    private Logger logger;

    @Autowired
    private DynamicFormQuestionRepository dynamicFormQuestionRepo;

    /**
     * Deleta uma questão do formulário dinâmico
     * 
     * @param id Identificador único da questão do formulário dinâmico
     */
    public void byId(Long id) {
        logger.info("Iniciando processo de remoção de questão do formulário dinâmico :: {}", id);
        DynamicFormQuestion entity = dynamicFormQuestionRepo.findById(id)
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                        String.format("Questão com o identificador %s não encontrado.", id)));
        logger.info("Questão encontrada :: Formulário -> {} :: Questão -> {}", entity.getForm().getTitle(), entity.getQuestion());
        dynamicFormQuestionRepo.delete(entity);
        logger.info("Remoção realizada com sucesso!");
    }

}
