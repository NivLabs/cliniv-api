package br.com.nivlabs.cliniv.models.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Classe AddressDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 18 de out de 2019
 */
@Schema(description = "Endereço")
public class AddressDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 5868142007921449692L;

    @Schema(description = "Rua do endereço")
    @NotNull(message = "Informe a rua do endereço")
    private String street;

    @Schema(description = "Número do imóvel")
    @NotNull(message = "Informe um número para o endereço")
    private String addressNumber;

    @Schema(description = "Complemento do endereço, se houver")
    private String complement;

    @Schema(description = "CEP do endereço")
    @Size(min = 8, max = 8, message = "O CEP deve conter 8 dígitos, apenas números")
    private String postalCode;

    @Schema(description = "Estado")
    @NotNull(message = "Informe o estado do endereço")
    private String state;

    @Schema(description = "Bairro")
    @NotNull(message = "Informe o bairro do endereço")
    private String neighborhood;

    @Schema(description = "Cidade")
    @NotNull(message = "Informe a cidade do endereço")
    private String city;

    public AddressDTO() {
        super();
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddressNumber() {
        return addressNumber;
    }

    public void setAddressNumber(String addressNumber) {
        this.addressNumber = addressNumber;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((addressNumber == null) ? 0 : addressNumber.hashCode());
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + ((complement == null) ? 0 : complement.hashCode());
        result = prime * result + ((neighborhood == null) ? 0 : neighborhood.hashCode());
        result = prime * result + ((postalCode == null) ? 0 : postalCode.hashCode());
        result = prime * result + ((state == null) ? 0 : state.hashCode());
        result = prime * result + ((street == null) ? 0 : street.hashCode());
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
        AddressDTO other = (AddressDTO) obj;
        if (addressNumber == null) {
            if (other.addressNumber != null)
                return false;
        } else if (!addressNumber.equals(other.addressNumber))
            return false;
        if (city == null) {
            if (other.city != null)
                return false;
        } else if (!city.equals(other.city))
            return false;
        if (complement == null) {
            if (other.complement != null)
                return false;
        } else if (!complement.equals(other.complement))
            return false;
        if (neighborhood == null) {
            if (other.neighborhood != null)
                return false;
        } else if (!neighborhood.equals(other.neighborhood))
            return false;
        if (postalCode == null) {
            if (other.postalCode != null)
                return false;
        } else if (!postalCode.equals(other.postalCode))
            return false;
        if (state == null) {
            if (other.state != null)
                return false;
        } else if (!state.equals(other.state))
            return false;
        if (street == null) {
            if (other.street != null)
                return false;
        } else if (!street.equals(other.street))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "AddressDTO [street=" + street + ", addressNumber=" + addressNumber + ", complement=" + complement + ", postalCode="
                + postalCode + ", state=" + state + ", neighborhood=" + neighborhood + ", city=" + city + "]";
    }

}
