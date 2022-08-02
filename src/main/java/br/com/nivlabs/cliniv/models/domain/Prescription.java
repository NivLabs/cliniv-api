package br.com.nivlabs.cliniv.models.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.nivlabs.cliniv.models.BaseObjectWithId;
import br.com.nivlabs.cliniv.models.converter.BooleanConverter;

@Entity
@Table(name = "PRESCRICAO")
public class Prescription extends BaseObjectWithId {

    private static final long serialVersionUID = -6245493538304106585L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_VISITA")
    private Attendance attendance;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_RESPONSAVEL")
    private Responsible responsible;

    @Column(name = "DATA_INICIO")
    private LocalDateTime datetimeInit;

    @Column(name = "DATA_FIM")
    private LocalDateTime datetimeEnd;

    @Column(name = "PRESCRICAO_ESPECIAL")
    @Convert(converter = BooleanConverter.class)
    private boolean special;

    @Column(name = "INSERE_EVENTO")
    @Convert(converter = BooleanConverter.class)
    private boolean insertToMedicalRecords;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_RESPONSAVEL_CANCEL")
    private Responsible responsibleForCancel;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "prescription")
    private List<PrescriptionItem> items = new ArrayList<>();

    public Prescription() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }

    public Responsible getResponsible() {
        return responsible;
    }

    public void setResponsible(Responsible responsible) {
        this.responsible = responsible;
    }

    public List<PrescriptionItem> getItems() {
        return items;
    }

    public void setItems(List<PrescriptionItem> items) {
        this.items = items;
    }

    public LocalDateTime getDatetimeInit() {
        return datetimeInit;
    }

    public void setDatetimeInit(LocalDateTime datetimeInit) {
        this.datetimeInit = datetimeInit;
    }

    public LocalDateTime getDatetimeEnd() {
        return datetimeEnd;
    }

    public void setDatetimeEnd(LocalDateTime datetimeEnd) {
        this.datetimeEnd = datetimeEnd;
    }

    public boolean isSpecial() {
        return special;
    }

    public void setSpecial(boolean special) {
        this.special = special;
    }

    public boolean isInsertToMedicalRecords() {
        return insertToMedicalRecords;
    }

    public void setInsertToMedicalRecords(boolean insertToMedicalRecords) {
        this.insertToMedicalRecords = insertToMedicalRecords;
    }

    /**
     * @return the responsibleForCancel
     */
    public Responsible getResponsibleForCancel() {
        return responsibleForCancel;
    }

    /**
     * @param responsibleForCancel the responsibleForCancel to set
     */
    public void setResponsibleForCancel(Responsible responsibleForCancel) {
        this.responsibleForCancel = responsibleForCancel;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Prescription [id=");
        builder.append(id);
        builder.append(", attendance=");
        builder.append(attendance);
        builder.append(", responsible=");
        builder.append(responsible);
        builder.append(", datetimeInit=");
        builder.append(datetimeInit);
        builder.append(", datetimeEnd=");
        builder.append(datetimeEnd);
        builder.append(", special=");
        builder.append(special);
        builder.append(", insertToMedicalRecords=");
        builder.append(insertToMedicalRecords);
        builder.append(", responsibleForCancel=");
        builder.append(responsibleForCancel);
        builder.append(", items=");
        builder.append(items);
        builder.append("]");
        return builder.toString();
    }

}
