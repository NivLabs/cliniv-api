package br.com.nivlabs.gp.models.domain;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import br.com.nivlabs.gp.enums.TimeIntervalType;
import br.com.nivlabs.gp.models.BaseObject;

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
