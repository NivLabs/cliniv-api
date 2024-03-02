package br.com.nivlabs.cliniv.models.domain;

import br.com.nivlabs.cliniv.enums.TimeIntervalType;
import br.com.nivlabs.cliniv.models.BaseObjectWithId;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "ITEM_PRESCRICAO")
public class PrescriptionItem extends BaseObjectWithId<ItemPrescriptionId> {

    @EmbeddedId
    private ItemPrescriptionId id;

    @Column(name = "DESCRICAO")
    private String description;

    @Column(name = "OBSERVACOES")
    private String observations;

    @Column(name = "VIA_ADM")
    private String routeOfAdministration;

    @Column(name = "DOSAGEM")
    private BigDecimal dosage;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_UNIDADE_MEDIDA")
    private UnitOfMeasurement unitOfMeasurement;

    @Column(name = "INTERVALO")
    private Integer timeInterval;

    @Column(name = "TIPO_INTERVALO")
    @Enumerated(EnumType.STRING)
    private TimeIntervalType timeIntervalType;

    @MapsId("prescriptionId")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "ID_PRESCRICAO")
    private Prescription prescription;

    public ItemPrescriptionId getId() {
        return id;
    }

    public void setId(ItemPrescriptionId id) {
        this.id = id;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getRouteOfAdministration() {
        return routeOfAdministration;
    }

    public void setRouteOfAdministration(String routeOfAdministration) {
        this.routeOfAdministration = routeOfAdministration;
    }

    public BigDecimal getDosage() {
        return dosage;
    }

    public void setDosage(BigDecimal dosage) {
        this.dosage = dosage;
    }

    public Integer getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(Integer timeInterval) {
        this.timeInterval = timeInterval;
    }

    public TimeIntervalType getTimeIntervalType() {
        return timeIntervalType;
    }

    public void setTimeIntervalType(TimeIntervalType timeIntervalType) {
        this.timeIntervalType = timeIntervalType;
    }

    public UnitOfMeasurement getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public void setUnitOfMeasurement(UnitOfMeasurement unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrescriptionItem that = (PrescriptionItem) o;
        return Objects.equals(id, that.id) && Objects.equals(description, that.description) && Objects.equals(observations, that.observations) && Objects.equals(routeOfAdministration, that.routeOfAdministration) && Objects.equals(dosage, that.dosage) && Objects.equals(unitOfMeasurement, that.unitOfMeasurement) && Objects.equals(timeInterval, that.timeInterval) && timeIntervalType == that.timeIntervalType && Objects.equals(prescription, that.prescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, observations, routeOfAdministration, dosage, unitOfMeasurement, timeInterval, timeIntervalType, prescription);
    }

    @Override
    public String toString() {
        return "PrescriptionItem{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", observations='" + observations + '\'' +
                ", routeOfAdministration='" + routeOfAdministration + '\'' +
                ", dosage=" + dosage +
                ", unitOfMeasurement=" + unitOfMeasurement +
                ", timeInterval=" + timeInterval +
                ", timeIntervalType=" + timeIntervalType +
                ", prescription=" + prescription +
                '}';
    }

}
