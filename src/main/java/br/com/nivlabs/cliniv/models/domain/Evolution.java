package br.com.nivlabs.cliniv.models.domain;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import br.com.nivlabs.cliniv.models.BaseObjectWithId;

/**
 * Entidade relacional que representa a evolução clínica do paciente no banco de dados
 * 
 * @author viniciosarodrigues
 *
 */
@Entity
@Table(name = "EVOLUCAO")
public class Evolution extends BaseObjectWithId {

    private static final long serialVersionUID = 2220797718806275886L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_VISITA")
    private Attendance attendance;

    @Column(name = "DESCRICAO")
    private String description;

    @Column(name = "DH_EVOLUCAO")
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private LocalDateTime datetime;

    public Evolution() {
        super();
    }

    public Evolution(Long id, Attendance attendance, String description, LocalDateTime datetime) {
        super();
        this.id = id;
        this.attendance = attendance;
        this.description = description;
        this.datetime = datetime;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((attendance == null) ? 0 : attendance.hashCode());
        result = prime * result + ((datetime == null) ? 0 : datetime.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        Evolution other = (Evolution) obj;
        if (attendance == null) {
            if (other.attendance != null)
                return false;
        } else if (!attendance.equals(other.attendance))
            return false;
        if (datetime == null) {
            if (other.datetime != null)
                return false;
        } else if (!datetime.equals(other.datetime))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Evolution [id=" + id + ", attendance=" + attendance + ", description=" + description + ", datetime=" + datetime + "]";
    }

}
