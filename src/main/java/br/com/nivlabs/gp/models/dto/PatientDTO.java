package br.com.nivlabs.gp.models.dto;

import java.time.LocalDate;

import br.com.nivlabs.gp.enums.Gender;
import br.com.nivlabs.gp.enums.PatientType;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Classe PatientDTO.java
 * 
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 * 
 * @since 15 de set de 2019
 */
@Schema(description = "Informações do Paciente")
public class PatientDTO extends PersonDTO {

    private static final long serialVersionUID = -1070682704153329772L;

    @Schema(description = "CNS")
    private String cnsNumber;

    @Schema(description = "Tipo do paciente (Identificado ou não identificado)")
    private PatientType type;

    public PatientDTO() {
    }

    /**
     * Construtor padrão para paginação
     * 
     * @param id
     * @param fullName
     * @param socialName
     * @param cpf
     * @param bornDate
     * @param principalNumber
     * @param gender
     * @param cnsNumber
     * @param type
     */
    public PatientDTO(Long id, String fullName, String socialName, String cpf, LocalDate bornDate, String principalNumber,
            Gender gender, String cnsNumber, PatientType type) {
        super.setId(id);
        super.setFullName(fullName);
        super.setSocialName(socialName);
        super.setCpf(cpf);
        super.setBornDate(bornDate);
        super.setPrincipalNumber(principalNumber);
        super.setGender(gender);
        this.cnsNumber = cnsNumber;
        this.type = type;
    }

    /**
     * @return the cnsNumber
     */
    public String getCnsNumber() {
        return cnsNumber;
    }

    /**
     * @param cnsNumber the cnsNumber to set
     */
    public void setCnsNumber(String cnsNumber) {
        this.cnsNumber = cnsNumber;
    }

    /**
     * @return the type
     */
    public PatientType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(PatientType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PatientDTO [cnsNumber=");
        builder.append(cnsNumber);
        builder.append(", type=");
        builder.append(type);
        builder.append("]");
        return builder.toString();
    }

}
