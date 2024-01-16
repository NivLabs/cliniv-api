package br.com.nivlabs.cliniv.models.domain;

import java.math.BigDecimal;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

import br.com.nivlabs.cliniv.enums.TimeIntervalType;
import br.com.nivlabs.cliniv.models.BaseObject;

@Entity
@Table(name = "ITEM_PRESCRICAO")
public class PrescriptionItem extends BaseObject {

    private static final long serialVersionUID = 7633223068100987676L;

    @EmbeddedId
    private ItemPrescriptionIdPK id;

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

    public ItemPrescriptionIdPK getId() {
        return id;
    }

    public void setId(ItemPrescriptionIdPK id) {
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
    public String toString() {
        return "PrescriptionItem ["
                + "id=" + id
                + ", description=" + description
                + ", observations=" + observations
                + ", routeOfAdministration=" + routeOfAdministration
                + ", dosage=" + dosage
                + ", unitOfMeasurement=" + unitOfMeasurement
                + ", timeInterval=" + timeInterval
                + ", timeIntervalType=" + timeIntervalType
                + "]";
    }

}
