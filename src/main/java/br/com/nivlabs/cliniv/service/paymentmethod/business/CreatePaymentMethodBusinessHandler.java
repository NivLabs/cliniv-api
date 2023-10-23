package br.com.nivlabs.cliniv.service.paymentmethod.business;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.nivlabs.cliniv.models.domain.PaymentMethod;
import br.com.nivlabs.cliniv.models.dto.PaymentMethodDTO;
import br.com.nivlabs.cliniv.repository.PaymentMethodRepository;
import br.com.nivlabs.cliniv.service.BaseBusinessHandler;

@Component
public class CreatePaymentMethodBusinessHandler implements BaseBusinessHandler {

    @Autowired
    private Logger logger;
    @Autowired
    private PaymentMethodRepository repo;

    public PaymentMethodDTO create(PaymentMethodDTO newPaymentMethod) {
        logger.info("Iniciando processo de criação de método de pagamento :: {}", newPaymentMethod.getName());
        PaymentMethod entity = new PaymentMethod(null, newPaymentMethod.getName());
        repo.save(entity);
        newPaymentMethod.setId(entity.getId());
        logger.info("Método de pagamento criado com sucesso :: {} | {}", newPaymentMethod.getId(), newPaymentMethod.getName());
        return newPaymentMethod;
    }

}
