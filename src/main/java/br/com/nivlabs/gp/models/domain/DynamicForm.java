package br.com.nivlabs.gp.models.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.nivlabs.gp.models.BaseObjectWithId;

@Entity
@Table(name = "ANAMNESE_FORM")
public class DynamicForm extends BaseObjectWithId {

    private static final long serialVersionUID = 362387950381152902L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TITULO")
    private String title;

    @OneToMany(cascade = CascadeType.DETACH, mappedBy = "form", fetch = FetchType.LAZY)
    private List<DynamicFormQuestion> questions = new ArrayList<>();

    public DynamicForm() {
        super();
    }

    public DynamicForm(Long id, String title, List<DynamicFormQuestion> questions) {
        super();
        this.id = id;
        this.title = title;
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "AnamnesisForm [id=" + id + ", title=" + title + ", questions=" + questions + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((questions == null) ? 0 : questions.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
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
        DynamicForm other = (DynamicForm) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (questions == null) {
            if (other.questions != null)
                return false;
        } else if (!questions.equals(other.questions))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<DynamicFormQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<DynamicFormQuestion> questions) {
        this.questions = questions;
    }

}
