package br.com.nivlabs.gp.models.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.BaseObjectWithId;
import br.com.nivlabs.gp.models.dto.AnamnesisDTO;
import br.com.nivlabs.gp.models.dto.DynamicFormQuestionDTO;

@Entity
@Table(name = "ANAMNESE")
public class Anamnesis extends BaseObjectWithId {
    private static final long serialVersionUID = -4203582800741543902L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_VISITA")
    private Attendance attendance;

    @Column(name = "PERGUNTA")
    private String question;

    @Column(name = "RESPOSTA")
    private String response;

    public Anamnesis() {
        super();
    }

    public Anamnesis(Long id, Attendance attendance, String question, String response) {
        super();
        this.id = id;
        this.attendance = attendance;
        this.question = question;
        this.response = response;
    }

    @JsonIgnore
    public AnamnesisDTO getAnamneseDTOFromDomain() {
        AnamnesisDTO dto = new AnamnesisDTO();

        dto.setId(id);
        dto.setAnamnesisItem(new DynamicFormQuestionDTO(null, question, null));
        dto.setAttendanceId(attendance.getId());
        dto.setResponse(response);

        return dto;
    }

    @PreUpdate
    protected void blockUpdate() {
        throw new HttpException(HttpStatus.METHOD_NOT_ALLOWED,
                "Método não autorizado -> Não é permitido alterar uma anamnese");
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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((attendance == null) ? 0 : attendance.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((question == null) ? 0 : question.hashCode());
        result = prime * result + ((response == null) ? 0 : response.hashCode());
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
        Anamnesis other = (Anamnesis) obj;
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
        if (question == null) {
            if (other.question != null)
                return false;
        } else if (!question.equals(other.question))
            return false;
        if (response == null) {
            if (other.response != null)
                return false;
        } else if (!response.equals(other.response))
            return false;
        return true;
    }

}
