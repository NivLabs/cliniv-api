package br.com.nivlabs.gp.models.domain;

import java.util.ArrayList;
import java.util.List;

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

import br.com.nivlabs.gp.enums.PatientType;
import br.com.nivlabs.gp.models.BaseObjectWithCreatedAt;

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
    private String susNumber;

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

    public String getSusNumber() {
        return susNumber;
    }

    public void setSusNumber(String susNumber) {
        this.susNumber = susNumber;
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
        return "Patient [id=" + id + ", susNumber=" + susNumber + ", person=" + person + ", healthPlan=" + healthPlan + ", healthPlanCode="
                + healthPlanCode + ", type=" + type + ", annotations=" + annotations + ", allergies=" + allergies + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((allergies == null) ? 0 : allergies.hashCode());
        result = prime * result + ((annotations == null) ? 0 : annotations.hashCode());
        result = prime * result + ((healthPlan == null) ? 0 : healthPlan.hashCode());
        result = prime * result + ((healthPlanCode == null) ? 0 : healthPlanCode.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((person == null) ? 0 : person.hashCode());
        result = prime * result + ((susNumber == null) ? 0 : susNumber.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        Patient other = (Patient) obj;
        if (allergies == null) {
            if (other.allergies != null)
                return false;
        } else if (!allergies.equals(other.allergies))
            return false;
        if (annotations == null) {
            if (other.annotations != null)
                return false;
        } else if (!annotations.equals(other.annotations))
            return false;
        if (healthPlan == null) {
            if (other.healthPlan != null)
                return false;
        } else if (!healthPlan.equals(other.healthPlan))
            return false;
        if (healthPlanCode == null) {
            if (other.healthPlanCode != null)
                return false;
        } else if (!healthPlanCode.equals(other.healthPlanCode))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (person == null) {
            if (other.person != null)
                return false;
        } else if (!person.equals(other.person))
            return false;
        if (susNumber == null) {
            if (other.susNumber != null)
                return false;
        } else if (!susNumber.equals(other.susNumber))
            return false;
        if (type != other.type)
            return false;
        return true;
    }

}
