package br.com.nivlabs.gp.service.dynamicform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.controller.filters.DynamicFormFilters;
import br.com.nivlabs.gp.models.dto.DynamicFormDTO;
import br.com.nivlabs.gp.models.dto.DynamicFormQuestionDTO;
import br.com.nivlabs.gp.models.dto.NewDynamicFormAnsweredDTO;
import br.com.nivlabs.gp.service.BaseService;
import br.com.nivlabs.gp.service.dynamicform.business.AnswerDynamicFormBusinessHandler;
import br.com.nivlabs.gp.service.dynamicform.business.CreateDynamicFormBusinessHandler;
import br.com.nivlabs.gp.service.dynamicform.business.CreateDynamicFormQuestionBusinessHandler;
import br.com.nivlabs.gp.service.dynamicform.business.DeleteDynamicFormBusinessHandler;
import br.com.nivlabs.gp.service.dynamicform.business.DeleteDynamicFormQuestionBusinessHandler;
import br.com.nivlabs.gp.service.dynamicform.business.SearchDynamicFormBusinessHandler;
import br.com.nivlabs.gp.service.dynamicform.business.UpdateDynamicFormBusinessHandler;
import br.com.nivlabs.gp.service.dynamicform.business.UpdateDynamicFormQuestionBusinessHandler;

/**
 * Camada de serviço de formulários dinâmicos da aplicação
 * 
 * @author viniciosarodrigues
 *
 */
@Service
public class DynamicFormService implements BaseService {

    @Autowired
    SearchDynamicFormBusinessHandler searchDynamicFormBusinessHandler;
    @Autowired
    DeleteDynamicFormBusinessHandler deleteDynamicFormBusinessHandler;
    @Autowired
    UpdateDynamicFormQuestionBusinessHandler updateDynamicFormQuestionBusinessHandler;
    @Autowired
    CreateDynamicFormQuestionBusinessHandler createDynamicFormQuestionBusinessHandler;
    @Autowired
    DeleteDynamicFormQuestionBusinessHandler deleteDynamicFormQuestionBusinessHandler;

    @Autowired
    AnswerDynamicFormBusinessHandler answerDynamicFormBusinessHandler;
    @Autowired
    CreateDynamicFormBusinessHandler createDynamicFormBusinessHandler;
    @Autowired
    UpdateDynamicFormBusinessHandler updateDynamicFormBusinessHandler;

    /**
     * Realiza a exclusão física do formulário dinâmico
     * 
     * @param id Identificador único do formulário dinâmico
     */
    public void deleteDynamicFormById(Long id) {
        deleteDynamicFormBusinessHandler.byId(id);
    }

    /**
     * Cria um novo questionário respondido de anamnese
     * 
     * @param request
     * @return
     */
    public NewDynamicFormAnsweredDTO newDynamicFormAnswered(Long attendanceId, NewDynamicFormAnsweredDTO request) {
        return answerDynamicFormBusinessHandler.answer(attendanceId, request);
    }

    /**
     * Busca uma página de formulárioes de anamnese
     * 
     * @param pageSettings Configurações de paginação
     * @return Página de formulários de anamnese
     */
    public Page<DynamicFormDTO> findPageOfDymicaForm(DynamicFormFilters filters, Pageable pageSettings) {
        return searchDynamicFormBusinessHandler.getPage(filters, pageSettings);
    }

    /**
     * Cria um formulário dinâmico na aplicação
     * 
     * @param request Informações para criação de formulário dinâmico
     * @return Formulário dinâmico criado
     */
    public DynamicFormDTO create(DynamicFormDTO request) {
        return createDynamicFormBusinessHandler.create(request);
    }

    /**
     * Atualiza informações do formulário dinâmico
     * 
     * @param id Identificador único do formulário
     * @param request Informações a serem atualizadas
     * @return Formulário atualizado
     */
    public DynamicFormDTO update(Long id, DynamicFormDTO request) {
        request.setId(id);
        return updateDynamicFormBusinessHandler.update(request);
    }

    /**
     * Deleta uma questão do formulário dinâmico
     * 
     * @param id Identificador único da questão do formulário dinâmico
     */
    public void deleteQuestionById(Long id) {
        deleteDynamicFormQuestionBusinessHandler.byId(id);
    }

    /**
     * Cria uma questão para o formulário dinâmico
     * 
     * @param idForm Identificador único do formulário dinâmico que terá uma questão adicionada
     * @param request Questão que será criada no formulário
     * @return Questão criada
     */
    public DynamicFormQuestionDTO createQuestion(Long idForm, DynamicFormQuestionDTO request) {
        return createDynamicFormQuestionBusinessHandler.create(idForm, request);
    }

    /**
     * Atualiza uma questão do formulário dinâmico
     * 
     * @param id Identificador único da questão do formulário
     * @param request Objeto com dados modificados da questão do formulário
     * @return Questão atualizada
     */
    public DynamicFormQuestionDTO updateQuestion(Long id, DynamicFormQuestionDTO request) {
        request.setId(id);
        return updateDynamicFormQuestionBusinessHandler.update(request);
    }

    /**
     * Busca um formulário dinâmico pelo identificador único do formulário
     * 
     * @param id Identificador único do formulário de Anamnese
     * @return Formulário dinâmico com título e perguntas
     */
    public DynamicFormDTO findById(Long id) {
        return searchDynamicFormBusinessHandler.byId(id);
    }

}
