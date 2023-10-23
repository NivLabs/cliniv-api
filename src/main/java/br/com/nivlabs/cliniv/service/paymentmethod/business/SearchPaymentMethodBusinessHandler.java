package br.com.nivlabs.cliniv.service.paymentmethod.business;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import br.com.nivlabs.cliniv.models.dto.PaymentMethodDTO;
import br.com.nivlabs.cliniv.repository.PaymentMethodRepository;
import br.com.nivlabs.cliniv.repository.custom.CustomFilters;
import br.com.nivlabs.cliniv.service.BaseBusinessHandler;

@Component
public class SearchPaymentMethodBusinessHandler implements BaseBusinessHandler {

    @Autowired
    private Logger logger;

    @Autowired
    private PaymentMethodRepository repo;

    public Page<PaymentMethodDTO> getPage(CustomFilters filters) {
        logger.info("Iniciando busca paginada de métodos de pagamentos");
        return repo.resumedList(filters);
    }

}
