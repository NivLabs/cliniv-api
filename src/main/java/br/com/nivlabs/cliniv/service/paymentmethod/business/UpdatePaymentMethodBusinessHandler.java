package br.com.nivlabs.cliniv.service.paymentmethod.business;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.PaymentMethod;
import br.com.nivlabs.cliniv.models.dto.PaymentMethodDTO;
import br.com.nivlabs.cliniv.repository.PaymentMethodRepository;
import br.com.nivlabs.cliniv.service.BaseBusinessHandler;

@Component
public class UpdatePaymentMethodBusinessHandler implements BaseBusinessHandler {

    @Autowired
    private Logger logger;
    @Autowired
    private PaymentMethodRepository repo;

    public PaymentMethodDTO update(PaymentMethodDTO paymentMethodUpdated) {
        logger.info("Iniciando processo de atualização de método de pagamento já existente :: {} | {}", paymentMethodUpdated.getId(),
                    paymentMethodUpdated.getName());

        PaymentMethod entity = repo.findById(paymentMethodUpdated.getId())
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND, "Método de pagamento não encontrado!"));

        logger.info("Atualizando método de pagamento :: {} para {}", entity.getName(), paymentMethodUpdated.getName());

        entity.setName(paymentMethodUpdated.getName());
        repo.save(entity);

        return paymentMethodUpdated;
    }

}
