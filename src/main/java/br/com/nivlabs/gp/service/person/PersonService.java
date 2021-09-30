package br.com.nivlabs.gp.service.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.models.dto.PersonInfoDTO;
import br.com.nivlabs.gp.service.BaseService;
import br.com.nivlabs.gp.service.person.business.CreatePersonBusinessHandler;
import br.com.nivlabs.gp.service.person.business.SearchPersonBusinessHandler;
import br.com.nivlabs.gp.service.person.business.UpdatePersonBusinessHandler;

/**
 * Classe PersonService.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 19 de out de 2019
 */
@Service
public class PersonService implements BaseService {

    @Autowired
    SearchPersonBusinessHandler searchPersonBusinessHandler;
    @Autowired
    UpdatePersonBusinessHandler updatePersonBusinessHandler;
    @Autowired
    CreatePersonBusinessHandler createPersonBusinessHandler;

    /**
     * Consulta pessoa por identificador único
     * 
     * @param id Identificador único da pessoa
     * @return Informações detalhadas de uma pessoa
     */
    public PersonInfoDTO findById(Long id) {
        return searchPersonBusinessHandler.byId(id);
    }

    /**
     * Consulta informações de pessoa física por CPF do cadastro de pessoa física
     * 
     * @param cpf CPF de pessoa física
     * @return Informações detalhadas do cadastro de pessoa física encontrado
     */
    public PersonInfoDTO findByCpf(String cpf) {
        return searchPersonBusinessHandler.byCPF(cpf);
    }

    /**
     * Atualiza informações do cadastro de pessoa física
     * 
     * @param request Informações à serem atualizadas
     * @return Informações atualizadas
     */
    public PersonInfoDTO update(PersonInfoDTO request) {
        return updatePersonBusinessHandler.update(request);
    }

    /**
     * Cria um novo cadastro de pessoa física na aplicação
     * 
     * @param request Informações à serem inseridas
     * @return Informações pós criação com Identificador único da pessoa física
     */
    public PersonInfoDTO create(PersonInfoDTO request) {
        return createPersonBusinessHandler.create(request);
    }

}
