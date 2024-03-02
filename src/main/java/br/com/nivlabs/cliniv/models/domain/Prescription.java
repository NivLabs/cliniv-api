package br.com.nivlabs.cliniv.models.domain;

import br.com.nivlabs.cliniv.models.BaseObjectWithId;
import br.com.nivlabs.cliniv.models.converter.BooleanConverter;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "PRESCRICAO")
public class Prescription extends BaseObjectWithId<Long> {

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prescription that = (Prescription) o;
        return special == that.special && insertToMedicalRecords == that.insertToMedicalRecords && Objects.equals(id, that.id) && Objects.equals(attendance, that.attendance) && Objects.equals(responsible, that.responsible) && Objects.equals(datetimeInit, that.datetimeInit) && Objects.equals(datetimeEnd, that.datetimeEnd) && Objects.equals(responsibleForCancel, that.responsibleForCancel) && Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, attendance, responsible, datetimeInit, datetimeEnd, special, insertToMedicalRecords, responsibleForCancel, items);
    }

    @Override
    public String toString() {
        return "Prescription{" +
                "id=" + id +
                ", attendance=" + attendance +
                ", responsible=" + responsible +
                ", datetimeInit=" + datetimeInit +
                ", datetimeEnd=" + datetimeEnd +
                ", special=" + special +
                ", insertToMedicalRecords=" + insertToMedicalRecords +
                ", responsibleForCancel=" + responsibleForCancel +
                ", items=" + items +
                '}';
    }

}
