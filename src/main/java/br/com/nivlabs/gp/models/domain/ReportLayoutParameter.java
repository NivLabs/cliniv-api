package br.com.nivlabs.gp.models.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.nivlabs.gp.models.BaseObject;

/**
 * Classe ReportLayoutParameter.java
 * 
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 * 
 * @since 24 de janeiro de 2021
 */
@Entity
@Table(name = "PARAMETROS_LAYOUT_RELATORIO")
public class ReportLayoutParameter extends BaseObject {

    private static final long serialVersionUID = -2781134021717644682L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOME")
    private String name;

    @Column(name = "TIPO")
    private String type;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "ID_LAYOUT_RELATORIO")
    private ReportLayout layout;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ReportLayout getLayout() {
        return layout;
    }

    public void setLayout(ReportLayout layout) {
        this.layout = layout;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ReportLayoutParameter [id=" + id + ", name=" + name + ", type=" + type + ", layout=" + layout + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((layout == null) ? 0 : layout.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        ReportLayoutParameter other = (ReportLayoutParameter) obj;
        if (layout == null) {
            if (other.layout != null)
                return false;
        } else if (!layout.equals(other.layout))
            return false;
        if (layout == null) {
            if (other.layout != null)
                return false;
        } else if (!layout.equals(other.layout))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }

}
