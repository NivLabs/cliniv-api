package br.com.nivlabs.cliniv.manager.service.customer.business;

import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.integration.email.EmailService;
import br.com.nivlabs.cliniv.manager.dto.NewCustomerDTO;
import br.com.nivlabs.cliniv.manager.models.Customer;
import br.com.nivlabs.cliniv.manager.repository.CustomerRepository;
import br.com.nivlabs.cliniv.service.BaseBusinessHandler;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Componente de Criação de cliente nas bases internas
 *
 * @author viniciosarodrigues
 */
@Component
public class CreateCustomerBusinessHandler implements BaseBusinessHandler {

    @Autowired
    private Logger logger;

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EmailService emailService;

    @Value("${nivlabs.api.baseUrl}")
    private String baseUrl;

    public void create(NewCustomerDTO request) {
        logger.info("Iniciando processo de cadastro de cliente nas bases internas...");
        if (customerRepository.findByCgc(request.getCgc()).isPresent()) {
            throw new HttpException(HttpStatus.CONFLICT, String
                    .format("Já existe um cadastro com o %s informado, se você é o proprietário do mesmo e não reconhece o cadastro, envie-nos um e-mail em atendimento@nivlabs.com.br com o assunto 'Cadastro não autorizado'.",
                            request.getCgcType().name()));
        }

        Customer customer = convertRequest(request);
        customer = customerRepository.save(customer);
        logger.info("Cliente cadastrado com sucesso :: Cod: {} | Nome do gestor: {}", customer.getId(), customer.getManagerName());
        sendConfirmationMail(customer);
    }

    /**
     * Envia e-mail de confirmação de novo registro
     *
     * @param customer Cliente cadastrado
     */
    private void sendConfirmationMail(Customer customer) {
        try {
            logger.info("Iniciando envio de e-mail de confirmação...");
            Map<String, Object> vars = new HashMap<>();
            vars.put("url", baseUrl + "/customer/confirm/" + customer.getUuid());
            vars.put("clinicName", customer.getClinicName());
            vars.put("managerName", customer.getManagerName());
            emailService
                    .sendHtmlMessage(emailService.prepareHtmlMessage("Confirmação CliNiv", "email/ativacao", vars, customer.getEmail()));
        } catch (Exception e) {
            logger.error("Falha ao tentar enviar o e-mail de confirmação!", e);
        }

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
        customer.setUuid(UUID.randomUUID().toString());
        return customer;
    }

}
