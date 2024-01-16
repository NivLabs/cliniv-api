package br.com.nivlabs.cliniv.models.domain;

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

import br.com.nivlabs.cliniv.models.BaseObject;

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

    @Column(name = "DESCRICAO")
    private String description;

    @Column(name = "VALOR_PADRAO")
    private String defaultValue;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_LAYOUT_RELATORIO")
    private ReportLayout layout;

    public ReportLayoutParameter(Long id, String name, String type, String description, String defaultValue, ReportLayout layout) {
        super();
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.defaultValue = defaultValue;
        this.layout = layout;
    }

    public ReportLayoutParameter() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
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
        return "ReportLayoutParameter [id=" + id + ", name=" + name + ", type=" + type + ", description=" + description + ", defaultValue="
                + defaultValue + ", layout=" + layout + "]";
    }

}
