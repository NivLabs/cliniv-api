package br.com.nivlabs.cliniv.models.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.nivlabs.cliniv.models.BaseObjectWithId;

@Entity
@Table(name = "METODO_PAGAMENTO")
public class PaymentMethod extends BaseObjectWithId {

    private static final long serialVersionUID = -7050156949633911352L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOME")
    private String name;

    public PaymentMethod() {
        super();
    }

    public PaymentMethod(Long id) {
        super();
        this.id = id;
    }

    public PaymentMethod(Long id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PaymentMethod [id=");
        builder.append(id);
        builder.append(", name=");
        builder.append(name);
        builder.append("]");
        return builder.toString();
    }

}
