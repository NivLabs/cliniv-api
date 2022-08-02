package br.com.nivlabs.cliniv.service.dynamicform.business;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.DynamicFormQuestion;
import br.com.nivlabs.cliniv.models.dto.DynamicFormQuestionDTO;
import br.com.nivlabs.cliniv.repository.DynamicFormQuestionRepository;
import br.com.nivlabs.cliniv.service.BaseBusinessHandler;

/**
 * 
 * Componente para atualização de questões do formulário dinâmico
 *
 * @author viniciosarodrigues
 * @since 24-09-2021
 *
 */
@Component
public class UpdateDynamicFormQuestionBusinessHandler implements BaseBusinessHandler {

    @Autowired
    private Logger logger;

    @Autowired
    private DynamicFormQuestionRepository dynamicFormQuestionRepo;

    /**
     * Atualiza uma questão do formulário dinâmico
     * 
     * @param id Identificador único da questão do formulário
     * @param request Objeto com dados modificados da questão do formulário
     * @return Questão atualizada
     */
    public DynamicFormQuestionDTO update(DynamicFormQuestionDTO request) {
        logger.info("Iniciando processo de atualização de questão de formulário :: {} | {} | {}", request.getId(), request.getQuestion(),
                    request.getMetaType());
        DynamicFormQuestion entity = dynamicFormQuestionRepo.findById(request.getId())
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                        String.format("Questão com o identificador %s não encontrado.", request.getId())));
        logger.info("Formulário à ser alterado :: {} | {} | {}", entity.getId(), entity.getQuestion(), entity.getMetaType());
        entity.setMetaType(request.getMetaType());
        entity.setQuestion(request.getQuestion());

        dynamicFormQuestionRepo.saveAndFlush(entity);
        logger.info("Formulário alterado com sucesso!");
        return request;
    }

}
