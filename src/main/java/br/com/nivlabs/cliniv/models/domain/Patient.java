package br.com.nivlabs.cliniv.models.domain;

import br.com.nivlabs.cliniv.enums.PatientType;
import br.com.nivlabs.cliniv.models.BaseObjectWithCreatedAt;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe Patient.java
 *
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 * @since 15 de set de 2019
 */
@Entity
@Table(name = "PACIENTE")
public class Patient extends BaseObjectWithCreatedAt<Long> {

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
        return "Patient{" +
                "id=" + id +
                ", cnsNumber='" + cnsNumber + '\'' +
                ", person=" + person +
                ", healthPlan=" + healthPlan +
                ", healthPlanCode='" + healthPlanCode + '\'' +
                ", type=" + type +
                ", annotations='" + annotations + '\'' +
                ", allergies=" + allergies +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return Objects.equals(id, patient.id) && Objects.equals(cnsNumber, patient.cnsNumber) && Objects.equals(person, patient.person) && Objects.equals(healthPlan, patient.healthPlan) && Objects.equals(healthPlanCode, patient.healthPlanCode) && type == patient.type && Objects.equals(annotations, patient.annotations) && Objects.equals(allergies, patient.allergies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cnsNumber, person, healthPlan, healthPlanCode, type, annotations, allergies);
    }
}
