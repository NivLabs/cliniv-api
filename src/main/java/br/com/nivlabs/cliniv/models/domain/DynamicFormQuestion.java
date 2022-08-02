package br.com.nivlabs.cliniv.models.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.nivlabs.cliniv.enums.MetaType;
import br.com.nivlabs.cliniv.models.BaseObjectWithId;
import br.com.nivlabs.cliniv.models.dto.DynamicFormQuestionDTO;

@Entity
@Table(name = "ANAMNESE_ITEM")
public class DynamicFormQuestion extends BaseObjectWithId {
    private static final long serialVersionUID = -5324023359826888215L;

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

    @JsonIgnore
    public DynamicFormQuestionDTO getAnamnesisItemDTOFromDomain() {
        DynamicFormQuestionDTO domain = new DynamicFormQuestionDTO();
        BeanUtils.copyProperties(this, domain);
        return domain;
    }

    public DynamicFormQuestion() {
        super();
    }

    public DynamicFormQuestion(Long id, String question, MetaType metaType) {
        super();
        this.id = id;
        this.question = question;
        this.metaType = metaType;
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((form == null) ? 0 : form.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((metaType == null) ? 0 : metaType.hashCode());
        result = prime * result + ((question == null) ? 0 : question.hashCode());
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
        DynamicFormQuestion other = (DynamicFormQuestion) obj;
        if (form == null) {
            if (other.form != null)
                return false;
        } else if (!form.equals(other.form))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (metaType != other.metaType)
            return false;
        if (question == null) {
            if (other.question != null)
                return false;
        } else if (!question.equals(other.question))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "AnamnesisItem [id=" + id + ", question=" + question + ", metaType=" + metaType + ", form=" + form + "]";
    }

}
