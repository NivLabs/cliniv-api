package br.com.nivlabs.cliniv.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;

/**
 * Classe BaseObject.java
 *
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * @since 6 de set de 2019
 */
@MappedSuperclass
public abstract class BaseObjectWithCreatedAt<T> extends BaseObjectWithId<T> {

    @Column(name = "DATA_CRIACAO")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime createdAt;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
