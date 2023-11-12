package br.com.nivlabs.cliniv.service.paymentmethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.nivlabs.cliniv.models.dto.PaymentMethodDTO;
import br.com.nivlabs.cliniv.repository.custom.CustomFilters;
import br.com.nivlabs.cliniv.service.BaseService;
import br.com.nivlabs.cliniv.service.paymentmethod.business.CreatePaymentMethodBusinessHandler;
import br.com.nivlabs.cliniv.service.paymentmethod.business.DeletePaymentMethodByIdBusinessHandler;
import br.com.nivlabs.cliniv.service.paymentmethod.business.SearchPaymentMethodBusinessHandler;
import br.com.nivlabs.cliniv.service.paymentmethod.business.UpdatePaymentMethodBusinessHandler;

/**
 * Camada de serviços para operações com métodos de pagamentos
 * 
 * @author viniciosarodrigues
 * @since 20 de out de 2023
 */
@Service
public class PaymentMethodService implements BaseService {

	@Autowired
	private SearchPaymentMethodBusinessHandler searchPaymentMethodBusinessHandler;
	@Autowired
	private CreatePaymentMethodBusinessHandler createPaymentMethodBusinessHandler;
	@Autowired
	private UpdatePaymentMethodBusinessHandler updatePaymentMethodBusinessHandler;
	@Autowired
	private DeletePaymentMethodByIdBusinessHandler deletePaymentMethodByIdBusinessHandler;

	/**
	 * Realiza uma busca paginada de métodos de pagamentos
	 * 
	 * @param filters Filtros de busca
	 * @return Página de métodos de pagamentos
	 */
	public Page<PaymentMethodDTO> getPage(CustomFilters filters) {
		return searchPaymentMethodBusinessHandler.getPage(filters);
	}

	/**
	 * Realiza a criação de um novo cadastro de um método de pagamento no sistema
	 * 
	 * @param request Novo método de pagamento
	 * @return Método de pagamento criado no sistema
	 */
	public PaymentMethodDTO create(PaymentMethodDTO request) {
		request.setId(null);
		return createPaymentMethodBusinessHandler.create(request);
	}

	/**
	 * Realiza a atualização de um cadastro de um método de pagamento já existente
	 * no sistema
	 * 
	 * @param id       Identificador único do método de pagamento
	 * @param resquest Alterações solicitadas para atualização cadastral
	 * @return Método de pagamento com os dados atualizados
	 */
	public PaymentMethodDTO update(Long id, PaymentMethodDTO request) {
		request.setId(id);
		return updatePaymentMethodBusinessHandler.update(request);
	}

	/**
	 * Realiza a exclusão de um método de pagamento por identificador único
	 * 
	 * @param id Identificador único do método de pagamento
	 */
	public void delete(Long id) {
		deletePaymentMethodByIdBusinessHandler.delete(id);
	}
}
