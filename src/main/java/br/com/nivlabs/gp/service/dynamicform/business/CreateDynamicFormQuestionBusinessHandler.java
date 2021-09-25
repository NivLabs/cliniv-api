package br.com.nivlabs.gp.service.dynamicform.business;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.DynamicForm;
import br.com.nivlabs.gp.models.domain.DynamicFormQuestion;
import br.com.nivlabs.gp.models.dto.DynamicFormQuestionDTO;
import br.com.nivlabs.gp.repository.DynamicFormQuestionRepository;
import br.com.nivlabs.gp.repository.DynamicFormRepository;
import br.com.nivlabs.gp.service.BaseBusinessHandler;

/**
 * 
 * Componente para criação de questões no formulário dinâmico
 *
 * @author viniciosarodrigues
 * @since 24-09-2021
 *
 */
@Component
public class CreateDynamicFormQuestionBusinessHandler implements BaseBusinessHandler {

    @Autowired
    private Logger logger;
    @Autowired
    private DynamicFormRepository dynamicFormRepo;
    @Autowired
    private DynamicFormQuestionRepository dynamicFormQuestionRepo;

    /**
     * Cria uma questão para o formulário dinâmico
     * 
     * @param idForm Identificador único do formulário dinâmico que terá uma questão adicionada
     * @param request Questão que será criada no formulário
     * @return Questão criada
     */
    public DynamicFormQuestionDTO create(Long idForm, DynamicFormQuestionDTO request) {
        logger.info("Iniciando processo de criação de questão do formulário dinâmico :: {} | {}", request.getQuestion(),
                    request.getMetaType());
        DynamicForm form = dynamicFormRepo.findById(idForm).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND, String
                .format("Formulários dinâmico com o identificador %s não encontrado, não será possível criar uma pergunta para o mesmo.",
                        idForm)));
        logger.info("Formulário localizado :: {} | {}", idForm, form.getTitle());

        logger.info("Criando entidade para criação da questão...");
        DynamicFormQuestion question = new DynamicFormQuestion();
        question.setForm(new DynamicForm(idForm, null, null));
        question.setMetaType(request.getMetaType());
        question.setQuestion(request.getQuestion());
        dynamicFormQuestionRepo.saveAndFlush(question);

        request.setId(question.getId());

        logger.info("Questão adicionada ao formulário :: FormId -> {} + QuestId -> {}", idForm, request.getId());

        return request;
    }

}
