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

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_LAYOUT_RELATORIO")
    private ReportLayout layout;

    public ReportLayoutParameter() {
        super();
    }

    public ReportLayoutParameter(Long id, String name, String type, ReportLayout layout) {
        super();
        this.id = id;
        this.name = name;
        this.type = type;
        this.layout = layout;
    }

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

}
