package br.com.nivlabs.gp.manager.service.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.manager.dto.NewCustomerDTO;
import br.com.nivlabs.gp.manager.service.customer.business.CreateCustomerBusinessHandler;
import br.com.nivlabs.gp.service.BaseService;

@Service
public class CustomerService implements BaseService {

    @Autowired
    private CreateCustomerBusinessHandler createCustomerBusiness;

    /**
     * Insere um novo cliente à tabela interna da aplicação
     * 
     * @param request Requisição de criação de novo cliente
     */
    public void persist(NewCustomerDTO request) {
        createCustomerBusiness.create(request);
    }

}
