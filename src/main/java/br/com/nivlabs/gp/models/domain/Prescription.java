package br.com.nivlabs.gp.models.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.nivlabs.gp.models.BaseObjectWithId;

@Entity
@Table(name = "PRESCRICAO")
public class Prescription extends BaseObjectWithId {

    private static final long serialVersionUID = -6245493538304106585L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_VISITA")
    private Attendance attendance;

    @ManyToOne
    @JoinColumn(name = "ID_RESPONSAVEL")
    private Responsible responsible;

    public Prescription(Long id, Attendance attendance, Responsible responsible) {
        super();
        this.id = id;
        this.attendance = attendance;
        this.responsible = responsible;
    }

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((attendance == null) ? 0 : attendance.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((responsible == null) ? 0 : responsible.hashCode());
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
        Prescription other = (Prescription) obj;
        if (attendance == null) {
            if (other.attendance != null)
                return false;
        } else if (!attendance.equals(other.attendance))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (responsible == null) {
            if (other.responsible != null)
                return false;
        } else if (!responsible.equals(other.responsible))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Prescription [id=" + id + ", attendance=" + attendance + ", responsible=" + responsible + "]";
    }

}
