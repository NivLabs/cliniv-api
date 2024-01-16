package br.com.nivlabs.cliniv.controller;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nivlabs.cliniv.controller.filters.PaymentMethodFilters;
import br.com.nivlabs.cliniv.event.CreatedResourceEvent;
import br.com.nivlabs.cliniv.models.dto.PaymentMethodDTO;
import br.com.nivlabs.cliniv.service.paymentmethod.PaymentMethodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Forma de pagamento", description = "Endpoint - Forma de pagamento")
@RestController
@RequestMapping(value = "/payment-method")
public class PaymentMethodController extends BaseController<PaymentMethodService> {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Operation(summary = "paymentMethod-get", description = "Busca uma página de métodos de pagamentos")
    @GetMapping
    @PreAuthorize("hasAnyRole('FORMA_PAG_LEITURA', 'FORMA_PAG_ESCRITA', 'EVENTO_ESCRITA', 'ADMIN')")
    public ResponseEntity<Page<PaymentMethodDTO>> getPageSectors(PaymentMethodFilters filters) {
        return ResponseEntity.ok(service.getPage(filters));
    }

    @Operation(summary = "paymentMethod-post", description = "Insere um novo método de pagamento na aplicação")
    @PostMapping
    @PreAuthorize("hasAnyRole('FORMA_PAG_ESCRITA', 'ADMIN')")
    public ResponseEntity<PaymentMethodDTO> persist(@Validated @RequestBody(required = true) PaymentMethodDTO newPaymentMethod,
                                                    HttpServletResponse response) {
        PaymentMethodDTO createdPaymentMethod = service.create(newPaymentMethod);

        publisher.publishEvent(new CreatedResourceEvent(this, response, createdPaymentMethod.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(createdPaymentMethod);

    }

    @Operation(summary = "paymentMethod-put", description = "Atualiza um método de pagamento na aplicação")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('FORMA_PAG_ESCRITA', 'ADMIN')")
    public ResponseEntity<PaymentMethodDTO> update(@PathVariable("id") Long id,
                                                   @Validated @RequestBody(required = true) PaymentMethodDTO paymentMethod) {
        return ResponseEntity.ok().body(service.update(id, paymentMethod));
    }

}
