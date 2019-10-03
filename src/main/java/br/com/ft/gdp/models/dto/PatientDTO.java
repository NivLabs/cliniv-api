package br.com.ft.gdp.models.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.ft.gdp.models.domain.Patient;
import br.com.ft.gdp.models.domain.PatientPhone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe PatientDTO.java
 * 
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 * 
 * @since 15 de set de 2019
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PatientDTO implements Serializable {

    private static final long serialVersionUID = -1070682704153329772L;

    private Long id;

    private String firstName;

    private String lastName;

    private String rg;

    private String cpf;

    @DateTimeFormat(iso = ISO.DATE)
    private Date bornDate;

    private Set<String> phones = new HashSet<>();

    private String gender;

    private String fatherName;

    private String motherName;

    @JsonIgnore
    public Patient getPatientDomainFromDTO() {
        Patient domain = new Patient();

        domain.setId(getId());
        domain.setFirstName(getFirstName());
        domain.setLastName(getLastName());
        domain.setRg(getRg());
        domain.setCpf(getCpf());
        domain.setBornDate(getBornDate());
        phones.forEach(phoneNumber -> {
            PatientPhone phone = new PatientPhone();
            phone.setPhoneNumber(phoneNumber);
            domain.getPhones().add(phone);

        });

        domain.setGender(getGender());
        domain.setFatherName(getFatherName());
        domain.setMotherName(getMotherName());

        return domain;
    }

}
