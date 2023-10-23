package br.com.nivlabs.cliniv.models.dto;

import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Método de pagamento")
public class PaymentMethodDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 1137913901298964137L;

    @Schema(description = "Identificador único do método de pagamento")
    private Long id;

    @Schema(description = "Nome do método de pagamento")
    @NotNull(message = "O nome do método de pagamento é obrigatório")
    private String name;

    public PaymentMethodDTO() {
        super();
    }

    public PaymentMethodDTO(Long id, String name) {
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
        builder.append("PaymentMethodDTO [id=");
        builder.append(id);
        builder.append(", name=");
        builder.append(name);
        builder.append("]");
        return builder.toString();
    }

}
