package br.com.nivlabs.cliniv.service.paymentmethod.business;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.PaymentMethod;
import br.com.nivlabs.cliniv.repository.PaymentMethodRepository;
import br.com.nivlabs.cliniv.service.BaseBusinessHandler;

@Component
public class DeletePaymentMethodByIdBusinessHandler implements BaseBusinessHandler {
	@Autowired
	private Logger logger;
	@Autowired
	private PaymentMethodRepository repo;

	public void delete(Long id) {
		logger.info("Iniciando processo de exclusão de método de pagamento...");
		PaymentMethod entity = repo.findById(id)
				.orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND, "Método de pagamento não encontrado"));
		logger.info("Método de pagamento encontrado :: {} | {}", entity.getId(), entity.getName());
		repo.delete(entity);
		logger.info("Método de pagamento removido com sucesso!");
	}
}
