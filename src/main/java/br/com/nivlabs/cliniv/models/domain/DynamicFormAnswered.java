package br.com.nivlabs.cliniv.models.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import br.com.nivlabs.cliniv.models.BaseObject;

@Entity
@Table(name = "FORMULARIO_RESPONDIDO")
public class DynamicFormAnswered extends BaseObject {

    private static final long serialVersionUID = -2766545587502322092L;

    @Id
    private Long id;
    @Column(name = "ID_USUARIO")
    private Long responsibleId;
    @Column(name = "ID_VISITA")
    private Long attendanceId;
    @Column(name = "QUESTAO")
    private String question;
    @Column(name = "RESPOSTA")
    private String answer;

    public DynamicFormAnswered() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getResponsibleId() {
        return responsibleId;
    }

    public void setResponsibleId(Long responsibleId) {
        this.responsibleId = responsibleId;
    }

    public Long getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Long attendanceId) {
        this.attendanceId = attendanceId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "DynamicFormAnswered [id=" + id + ", responsibleId=" + responsibleId + ", attendanceId=" + attendanceId + ", question="
                + question + ", answer=" + answer + "]";
    }

}
