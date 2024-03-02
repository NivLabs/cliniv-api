package br.com.nivlabs.cliniv.models.domain;

import br.com.nivlabs.cliniv.models.BaseObjectWithId;
import jakarta.persistence.*;

import java.util.Objects;

/**
 * Classe ReportLayoutParameter.java
 *
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 * @since 24 de janeiro de 2021
 */
@Entity
@Table(name = "PARAMETROS_LAYOUT_RELATORIO")
public class ReportLayoutParameter extends BaseObjectWithId<Long> {

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportLayoutParameter that = (ReportLayoutParameter) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(type, that.type) && Objects.equals(description, that.description) && Objects.equals(defaultValue, that.defaultValue) && Objects.equals(layout, that.layout);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, description, defaultValue, layout);
    }

    @Override
    public String toString() {
        return "ReportLayoutParameter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", defaultValue='" + defaultValue + '\'' +
                ", layout=" + layout +
                '}';
    }
}
