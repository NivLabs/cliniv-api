package br.com.ft.gdp.models.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.ft.gdp.models.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe User.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 6 de set de 2019
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USUARIO")
public class UserApplication extends BaseObject {

    private static final long serialVersionUID = -4066717030226233952L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PESSOA")
    private Person person;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "USUARIO")
    private String userName;

    @JsonIgnore
    @Column(name = "SENHA", nullable = false, length = 500)
    private String password;

    @Column(name = "ATIVO")
    private boolean active;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USUARIO_PERMISSAO", joinColumns = {@JoinColumn(name = "ID_USUARIO")}, inverseJoinColumns = {@JoinColumn(name = "ID_PERMISSAO")})
    private List<Role> roles = new ArrayList<>();

    /**
     * @param userId
     */
    public UserApplication(Long userId) {
        this.id = userId;
    }

}
