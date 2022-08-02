package br.com.nivlabs.cliniv.manager.service.customer.business;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.nivlabs.cliniv.integration.email.EmailService;
import br.com.nivlabs.cliniv.manager.models.Customer;
import br.com.nivlabs.cliniv.manager.repository.CustomerRepository;
import br.com.nivlabs.cliniv.service.BaseBusinessHandler;

/**
 * Componente de confirmação de registro
 * 
 * @author viniciosarodrigues
 *
 */
@Component
public class ConfirmCustomerBusinessHandler implements BaseBusinessHandler {

    @Autowired
    private Logger logger;

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EmailService emailService;

    @Value("${nivlabs.ui.baseUrl}")
    private String baseUiUrl;

    @Transactional
    public String confirmUUID(String uuid) {
        logger.info("Iniciando processo de confirmação de cadastro...");
        Optional<Customer> customer = customerRepository.findByUuid(uuid);
        if (customer.isEmpty()) {
            logger.info("UUID {} não localizado, redirecionando para página de cadastro não encontrado...");
            return "https://www.nivlabs.com.br/customerNotFound.html";
        } else {

            Customer confirmedCustomer = customer.get();
            confirmedCustomer.setConfirmationDateTime(LocalDateTime.now());
            logger.info("Cliente encontrado, iniciando processo de confirmação :: {} | {} | {}", confirmedCustomer.getId(),
                        confirmedCustomer.getClinicName(), confirmedCustomer.getManagerName());
            customerRepository.save(confirmedCustomer);
            sendWellcomeEmail(confirmedCustomer);
            return "https://www.nivlabs.com.br/confirmedActivation.html";
        }

    }

    /**
     * Envia e-mail de boas vindas...
     * 
     * @param customer Cliente registrado e confirmado
     */
    private void sendWellcomeEmail(Customer customer) {
        try {
            logger.info("Iniciando envio de e-mail de boas vindas...");
            Map<String, Object> vars = new HashMap<>();
            vars.put("url", baseUiUrl);
            emailService
                    .sendHtmlMessage(emailService.prepareHtmlMessage("Boas-vindas da NivLabs", "email/boasvindas", vars, customer.getEmail()));
        } catch (MessagingException e) {
            logger.error("Falha ao tentar enviar o e-mail de boas vindas!", e);
        }
    }
}
