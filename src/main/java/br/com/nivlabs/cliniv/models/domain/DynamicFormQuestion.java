package br.com.nivlabs.cliniv.models.domain;

import br.com.nivlabs.cliniv.enums.MetaType;
import br.com.nivlabs.cliniv.models.BaseObjectWithId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "ANAMNESE_ITEM")
public class DynamicFormQuestion extends BaseObjectWithId<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PERGUNTA")
    private String question;

    @Column(name = "TIPO")
    @Enumerated(EnumType.STRING)
    private MetaType metaType;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ANAMNESE_FORM")
    @JsonIgnore
    private DynamicForm form;

    public DynamicFormQuestion() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public MetaType getMetaType() {
        return metaType;
    }

    public void setMetaType(MetaType metaType) {
        this.metaType = metaType;
    }

    public DynamicForm getForm() {
        return form;
    }

    public void setForm(DynamicForm form) {
        this.form = form;
    }

    @Override
    public String toString() {
        return "DynamicFormQuestion{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", metaType=" + metaType +
                ", form=" + form +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DynamicFormQuestion that = (DynamicFormQuestion) o;
        return Objects.equals(id, that.id) && Objects.equals(question, that.question) && metaType == that.metaType && Objects.equals(form, that.form);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, question, metaType, form);
    }
}
