package br.com.nivlabs.cliniv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nivlabs.cliniv.models.domain.PaymentMethod;
import br.com.nivlabs.cliniv.repository.custom.paymentmethod.PaymentMethodRepositoryCustom;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long>, PaymentMethodRepositoryCustom {

}
