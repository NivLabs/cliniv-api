package br.com.nivlabs.cliniv.repository.custom.paymentmethod;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import br.com.nivlabs.cliniv.controller.filters.PaymentMethodFilters;
import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.PaymentMethod;
import br.com.nivlabs.cliniv.models.domain.PaymentMethod_;
import br.com.nivlabs.cliniv.models.dto.PaymentMethodDTO;
import br.com.nivlabs.cliniv.repository.custom.CustomFilters;
import br.com.nivlabs.cliniv.repository.custom.GenericCustomRepository;
import br.com.nivlabs.cliniv.util.StringUtils;

public class PaymentMethodRepositoryCustomImpl extends GenericCustomRepository<PaymentMethod, PaymentMethodDTO>
        implements PaymentMethodRepositoryCustom {

    @Override
    public Page<PaymentMethodDTO> resumedList(CustomFilters filters) {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<PaymentMethodDTO> criteria = builder.createQuery(PaymentMethodDTO.class);
        Root<PaymentMethod> root = criteria.from(PaymentMethod.class);

        criteria.select(builder.construct(PaymentMethodDTO.class,
                                          root.get(PaymentMethod_.id),
                                          root.get(PaymentMethod_.name)));
        return getPage(filters, builder, criteria, root);
    }

    @Override
    protected Predicate[] createRestrictions(CustomFilters customFilters, CriteriaBuilder builder, Root<PaymentMethod> root) {
        if (!(customFilters instanceof PaymentMethodFilters filters)) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "O filtro enviado não é um filtro de método de pagamento");
        }
        List<Predicate> predicates = new ArrayList<>();
        if (!StringUtils.isNullOrEmpty(filters.getId()) && StringUtils.isNumeric(filters.getId())) {
            predicates.add(builder.equal(root.get(PaymentMethod_.id), Long.parseLong(filters.getId())));
        }
        if (!StringUtils.isNullOrEmpty(filters.getName())) {
            predicates.add(builder.like(root.get(PaymentMethod_.name), filters.getName()));
        }
        return predicates.toArray(new Predicate[predicates.size()]);
    }

}
