package br.com.nivlabs.gp.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * Classe BaseObject.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 6 de set de 2019
 */
@MappedSuperclass
public abstract class BaseObjectWithCreatedAt extends BaseObject {

    private static final long serialVersionUID = -230648426246848016L;

    @Column(name = "DATA_CRIACAO")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Getter
    @Setter
    private LocalDateTime createdAt;
}
