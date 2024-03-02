package br.com.nivlabs.cliniv.models.domain;

import br.com.nivlabs.cliniv.models.BaseObjectWithId;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Entidade relacional que representa a evolução clínica do paciente no banco de dados
 *
 * @author viniciosarodrigues
 */
@Entity
@Table(name = "EVOLUCAO")
public class Evolution extends BaseObjectWithId<Long> {

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Evolution evolution = (Evolution) o;
        return Objects.equals(id, evolution.id) && Objects.equals(attendance, evolution.attendance) && Objects.equals(description, evolution.description) && Objects.equals(datetime, evolution.datetime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, attendance, description, datetime);
    }

    @Override
    public String toString() {
        return "Evolution{" +
                "id=" + id +
                ", attendance=" + attendance +
                ", description='" + description + '\'' +
                ", datetime=" + datetime +
                '}';
    }
}
