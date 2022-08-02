package br.com.nivlabs.cliniv.models.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.nivlabs.cliniv.enums.PatientType;
import br.com.nivlabs.cliniv.models.BaseObjectWithCreatedAt;

/**
 * Classe Patient.java
 * 
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 * 
 * @since 15 de set de 2019
 */
@Entity
@Table(name = "PACIENTE")
public class Patient extends BaseObjectWithCreatedAt {

    private static final long serialVersionUID = 4873898002597934236L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CODIGO_SUS")
    private String cnsNumber;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "ID_PESSOA")
    private Person person;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_PLANO")
    private HealthPlan healthPlan;

    @Column(name = "NUMERO_PLANO")
    private String healthPlanCode;

    @Column(name = "TIPO_PACIENTE")
    @Enumerated(EnumType.STRING)
    private PatientType type;

    @Column(name = "ANOTACOES")
    private String annotations;

    @OneToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PACIENTE", referencedColumnName = "ID")
    private List<PatientAllergy> allergies = new ArrayList<>();

    public Patient(Long patientId) {
        this.id = patientId;
    }

    public Patient() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCnsNumber() {
        return cnsNumber;
    }

    public void setCnsNumber(String cnsNumber) {
        this.cnsNumber = cnsNumber;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public HealthPlan getHealthPlan() {
        return healthPlan;
    }

    public void setHealthPlan(HealthPlan healthPlan) {
        this.healthPlan = healthPlan;
    }

    public String getHealthPlanCode() {
        return healthPlanCode;
    }

    public void setHealthPlanCode(String healthPlanCode) {
        this.healthPlanCode = healthPlanCode;
    }

    public PatientType getType() {
        return type;
    }

    public void setType(PatientType type) {
        this.type = type;
    }

    public String getAnnotations() {
        return annotations;
    }

    public void setAnnotations(String annotations) {
        this.annotations = annotations;
    }

    public List<PatientAllergy> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<PatientAllergy> allergies) {
        this.allergies = allergies;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Patient [id=");
        builder.append(id);
        builder.append(", cnsNumber=");
        builder.append(cnsNumber);
        builder.append(", person=");
        builder.append(person);
        builder.append(", healthPlan=");
        builder.append(healthPlan);
        builder.append(", healthPlanCode=");
        builder.append(healthPlanCode);
        builder.append(", type=");
        builder.append(type);
        builder.append(", annotations=");
        builder.append(annotations);
        builder.append(", allergies=");
        builder.append(allergies);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(allergies, annotations, cnsNumber, healthPlan, healthPlanCode, id, person, type);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Patient other = (Patient) obj;
        return Objects.equals(allergies, other.allergies) && Objects.equals(annotations, other.annotations)
                && Objects.equals(cnsNumber, other.cnsNumber) && Objects.equals(healthPlan, other.healthPlan)
                && Objects.equals(healthPlanCode, other.healthPlanCode) && Objects.equals(id, other.id)
                && Objects.equals(person, other.person) && type == other.type;
    }

}
