package br.com.nivlabs.gp.manager.service.customer.business;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.manager.dto.NewCustomerDTO;
import br.com.nivlabs.gp.manager.models.Customer;
import br.com.nivlabs.gp.manager.repository.CustomerRepository;
import br.com.nivlabs.gp.service.BaseBusinessHandler;

/**
 * Componente de Criação de cliente nas bases internas
 * 
 * @author viniciosarodrigues
 *
 */
@Component
public class CreateCustomerBusinessHandler implements BaseBusinessHandler {

    @Autowired
    private Logger logger;

    @Autowired
    private CustomerRepository customerRepository;

    public void create(NewCustomerDTO request) {
        logger.info("Iniciando processo de cadastro de cliente nas bases internas...");
        if (customerRepository.findByCgc(request.getCgc()).isPresent()) {
            throw new HttpException(HttpStatus.CONFLICT, String
                    .format("Já existe um cadastro com o %s informado, se você é o proprietário do mesmo e não reconhece o cadastro, envie-nos um e-mail em atendimento@nivlabs.com.br com o assunto 'Cadastro não autorizado'.",
                            request.getCgcType().name()));
        }

        Customer customer = convertRequest(request);
        customerRepository.save(customer);
    }

    /**
     * Converte requisição em objeto domínio
     * 
     * @param request Requisição de criação de novo cliente
     * @return Entidade relacional de cliente
     */
    private Customer convertRequest(NewCustomerDTO request) {
        Customer customer = new Customer();
        customer.setClinicName(request.getClinicName());
        customer.setCgcType(request.getCgcType());
        customer.setCgc(request.getCgc());
        customer.setEmail(request.getEmail());
        customer.setManagerName(request.getManagerName());
        customer.setPrincipalPhone(request.getPrincipalPhone());
        return customer;
    }

}
