package br.com.nivlabs.cliniv.models.domain;

import br.com.nivlabs.cliniv.models.BaseObjectWithId;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "LEMBRETE")
public class Sticker extends BaseObjectWithId<Long> {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DH_CRIACAO")
    private LocalDateTime createdAt;

    @Column(name = "DESCRICAO")
    private String description;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO")
    private UserApplication user;

    public Sticker() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserApplication getUser() {
        return user;
    }

    public void setUser(UserApplication user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sticker sticker = (Sticker) o;
        return Objects.equals(id, sticker.id) && Objects.equals(createdAt, sticker.createdAt) && Objects.equals(description, sticker.description) && Objects.equals(user, sticker.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdAt, description, user);
    }

    @Override
    public String toString() {
        return "Sticker{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", description='" + description + '\'' +
                ", user=" + user +
                '}';
    }

}
