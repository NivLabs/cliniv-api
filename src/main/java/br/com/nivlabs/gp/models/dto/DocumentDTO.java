package br.com.nivlabs.gp.models.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;

import br.com.nivlabs.gp.enums.DocumentType;
import br.com.nivlabs.gp.exception.HttpException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Classe DocumentDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 31 de out de 2019
 */

@ApiModel(description = "Documento")
public class DocumentDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 8977997168527769344L;

    @ApiModelProperty("Tipo do documento pessoal, ex: CPF, CNPJ, RG, CNPJ")
    @NotNull(message = "O tipo do documeno deve ser informado")
    @NotEmpty(message = "O tipo do documeno deve ser informado")
    @NotBlank(message = "O tipo do documeno deve ser informado")
    private final DocumentType type;

    @ApiModelProperty("Valor do documento pessoal")
    @NotNull(message = "O documeno deve ser informado")
    @NotEmpty(message = "O documeno deve ser informado")
    @NotBlank(message = "O documento deve ser informado")
    private String value;

    public DocumentDTO(final DocumentType type, final String value) {
        super();
        checkDocumentTypeWithValuePattern(type, value);
        this.type = type;
        this.value = value;
    }

    public DocumentType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DocumentDTO other = (DocumentDTO) obj;
        if (type != other.type)
            return false;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "DocumentDTO [type=" + type + ", value=" + value + "]";
    }

    private void checkDocumentTypeWithValuePattern(DocumentType type, String value) {
        switch (type) {
            case RG:
                checkRG(value);
                break;
            case CNPJ:
                checkCNPJ(value);
                break;
            case CPF:
                checkCPF(value);
                break;
            case PASSPORT, RNE, SUS:
                break;
            default:
                throw new HttpException(HttpStatus.BAD_REQUEST, String.format("Tipo de documento inexistente :: %s", type));
        }

    }

    /**
     * Checa se o documento informado é um CPF válido
     * 
     * @param value Número do CPF
     */
    private void checkCPF(String value) {

    }

    /**
     * Checa se o CNPJ é um CNPJ Válido
     * 
     * @param value Número do CNPJ
     */
    private void checkCNPJ(String value) {

    }

    /**
     * Checa se a identidade é um documento válido
     * 
     * @param value
     */
    private void checkRG(String value) {

    }

}
