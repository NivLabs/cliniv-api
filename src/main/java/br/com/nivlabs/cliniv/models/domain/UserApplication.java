package br.com.nivlabs.cliniv.models.domain;

import br.com.nivlabs.cliniv.models.BaseObjectWithCreatedAt;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe User.java
 *
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * @since 6 de set de 2019
 */
@Entity
@Table(name = "USUARIO")
public class UserApplication extends BaseObjectWithCreatedAt<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "ID_PESSOA")
    private Person person;

    @Column(name = "USUARIO")
    private String userName;

    @JsonIgnore
    @Column(name = "SENHA", nullable = false, length = 500)
    private String password;

    @Column(name = "ATIVO")
    private boolean active;

    @Column(name = "PRIMEIRO_LOGIN")
    private boolean firstSignin;

    @Column(name = "ULTIMO_ACESSO")
    private LocalDateTime lastAcess;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USUARIO_PERMISSAO", joinColumns = {@JoinColumn(name = "ID_USUARIO")}, inverseJoinColumns = {@JoinColumn(name = "ID_PERMISSAO")})
    private List<Role> roles = new ArrayList<>();

    public UserApplication() {
        super();
    }

    public UserApplication(Long id, Person person, String userName, String password, boolean active, boolean firstSignin,
                           LocalDateTime lastAcess, List<Role> roles) {
        super();
        this.id = id;
        this.person = person;
        this.userName = userName;
        this.password = password;
        this.active = active;
        this.firstSignin = firstSignin;
        this.lastAcess = lastAcess;
        this.roles = roles;
    }

    /**
     * @param userId
     */
    public UserApplication(Long userId) {
        this.id = userId;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the person
     */
    public Person getPerson() {
        return person;
    }

    /**
     * @param person the person to set
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * @return the firstSignin
     */
    public boolean isFirstSignin() {
        return firstSignin;
    }

    /**
     * @param firstSignin the firstSignin to set
     */
    public void setFirstSignin(boolean firstSignin) {
        this.firstSignin = firstSignin;
    }

    /**
     * @return the roles
     */
    public List<Role> getRoles() {
        return roles;
    }

    public LocalDateTime getLastAcess() {
        return lastAcess;
    }

    public void setLastAcess(LocalDateTime lastAcess) {
        this.lastAcess = lastAcess;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserApplication that = (UserApplication) o;
        return active == that.active && firstSignin == that.firstSignin && Objects.equals(id, that.id) && Objects.equals(person, that.person) && Objects.equals(userName, that.userName) && Objects.equals(password, that.password) && Objects.equals(lastAcess, that.lastAcess) && Objects.equals(roles, that.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, person, userName, password, active, firstSignin, lastAcess, roles);
    }

    @Override
    public String toString() {
        return "UserApplication{" +
                "id=" + id +
                ", person=" + person +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", active=" + active +
                ", firstSignin=" + firstSignin +
                ", lastAcess=" + lastAcess +
                ", roles=" + roles +
                '}';
    }
}
