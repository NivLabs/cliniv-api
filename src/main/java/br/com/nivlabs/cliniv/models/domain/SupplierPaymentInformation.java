package br.com.nivlabs.cliniv.models.domain;

import br.com.nivlabs.cliniv.models.BaseObjectWithId;

// @Entity
// @Table(name = "FORNEC_INFO_PAGTO")
public class SupplierPaymentInformation extends BaseObjectWithId {

    private static final long serialVersionUID = 4645400354307413923L;

    private Long id;

    private Supplier supplier;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

}
