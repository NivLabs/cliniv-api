package br.com.nivlabs.cliniv.models.dto;

import java.time.LocalDate;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import br.com.nivlabs.cliniv.enums.DocumentType;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Classe DocumentDTO.java
 *
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * @since 31 de out de 2019
 */

@Schema(description = "Documento")
public class DocumentDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 8977997168527769344L;

    @Schema(description = "Identificador único da pessoa")
    private Long personId;

    @Schema(description = "Tipo do documento pessoal, ex: CPF, CNPJ, RG, CNPJ")
    @NotNull(message = "O tipo do documeno deve ser informado")
    @NotEmpty(message = "O tipo do documeno deve ser informado")
    @NotBlank(message = "O tipo do documeno deve ser informado")
    @Enumerated(EnumType.STRING)
    private DocumentType type;

    @Schema(description = "Valor do documento pessoal")
    @NotNull(message = "O documeno deve ser informado")
    @NotEmpty(message = "O documeno deve ser informado")
    @NotBlank(message = "O documento deve ser informado")
    private String value;

    @Schema(description = "Órgão expedidor")
    private String dispatcher;

    @Schema(description = "Data de expedição")
    private LocalDate expeditionDate;

    @Schema(description = "Data de Validade do documento")
    private LocalDate validate;

    @Schema(description = "UF de expedição")
    private String uf;

    public DocumentDTO() {
        super();
    }

    public DocumentDTO(Long personId,
                       @NotNull(message = "O tipo do documeno deve ser informado") @NotEmpty(message = "O tipo do documeno deve ser informado") @NotBlank(message = "O tipo do documeno deve ser informado") DocumentType type,
                       @NotNull(message = "O documeno deve ser informado") @NotEmpty(message = "O documeno deve ser informado") @NotBlank(message = "O documento deve ser informado") String value,
                       String dispatcher, LocalDate expeditionDate, LocalDate validate, String uf) {
        super();
        this.personId = personId;
        this.type = type;
        this.value = value;
        this.dispatcher = dispatcher;
        this.expeditionDate = expeditionDate;
        this.validate = validate;
        this.uf = uf;
    }

    public DocumentDTO(DocumentType type, String value) {
        this.type = type;
        this.value = value;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public DocumentType getType() {
        return type;
    }

    public void setType(DocumentType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(String dispatcher) {
        this.dispatcher = dispatcher;
    }

    public LocalDate getExpeditionDate() {
        return expeditionDate;
    }

    public void setExpeditionDate(LocalDate expeditionDate) {
        this.expeditionDate = expeditionDate;
    }

    public LocalDate getValidate() {
        return validate;
    }

    public void setValidate(LocalDate validate) {
        this.validate = validate;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    @Override
    public String toString() {
        return "DocumentDTO [personId=" + personId + ", type=" + type + ", value=" + value + ", dispatcher=" + dispatcher
                + ", expeditionDate=" + expeditionDate + ", validate=" + validate + ", uf=" + uf + "]";
    }

}
