package br.com.ft.gdp.models.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.ft.gdp.models.BaseObject;
import br.com.ft.gdp.models.dto.AddressDTO;
import br.com.ft.gdp.models.dto.PatientDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe Patient.java
 * 
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 * 
 * @since 15 de set de 2019
 */
@Entity
@Table(name = "PACIENTE")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Patient extends BaseObject {

    private static final long serialVersionUID = 4873898002597934236L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_PESSOA")
    private Person person;

    @OneToMany
    @JoinColumn(name = "ID_PACIENTE")
    private Set<PatientPhone> phones = new HashSet<>();

    @JsonIgnore
    public PatientDTO getPatientDTOFromDomain() {
        PatientDTO dtoEntity = new PatientDTO();

        dtoEntity.setId(getId());
        dtoEntity.setFirstName(getPerson().getFirstName());
        dtoEntity.setLastName(getPerson().getLastName());
        dtoEntity.setCpf(getPerson().getCpf());
        dtoEntity.setBornDate(getPerson().getBornDate());
        dtoEntity.setPhones(phones.stream().map(PatientPhone::getPhoneNumber).collect(Collectors.toSet()));
        dtoEntity.setGender(getPerson().getGender());
        dtoEntity.setFatherName(getPerson().getFatherName());
        dtoEntity.setMotherName(getPerson().getMotherName());
        if (!getPerson().getListOfAddress().isEmpty()) {
            AddressDTO address = new AddressDTO();
            BeanUtils.copyProperties(getPerson().getListOfAddress().get(0), address);
            dtoEntity.setAddress(address);
        }
        return dtoEntity;
    }

}
