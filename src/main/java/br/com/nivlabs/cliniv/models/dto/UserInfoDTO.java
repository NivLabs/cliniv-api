package br.com.nivlabs.cliniv.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Esta classe representa as informações detalhadas do usuário, serve para criação e atualização das informações do usuário
 *
 * @author viniciosarodrigues
 */
@Schema(description = "Informações detalhadas do usuário")
public class UserInfoDTO extends PersonInfoDTO {


    @Schema(description = "Nome de usuário")
    @NotNull(message = "Informe o nome de usuário")
    @Size(max = 80, min = 2, message = "O nome de usuário deve conter de 2 a 80 caracteres")
    private String userName;

    @Schema(description = "Data de criação do usuário")
    private LocalDateTime createdAt;

    @Schema(description = "Situação cadastral (Ativo | Inativo)")
    private boolean active;

    @Schema(description = "Flag de primeiro login")
    private boolean firstSignin;

    @Schema(description = "Papéis do usuário")
    private List<RoleDTO> roles = new ArrayList<>();

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
     * @return the createdAt
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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
    public List<RoleDTO> getRoles() {
        return roles;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(List<RoleDTO> roles) {
        this.roles = roles;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(active, createdAt, firstSignin, roles, userName);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserInfoDTO other = (UserInfoDTO) obj;
        return active == other.active && Objects.equals(createdAt, other.createdAt) && firstSignin == other.firstSignin
                && Objects.equals(roles, other.roles) && Objects.equals(userName, other.userName);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("UserInfoDTO [userName=");
        builder.append(userName);
        builder.append(", createdAt=");
        builder.append(createdAt);
        builder.append(", active=");
        builder.append(active);
        builder.append(", firstSignin=");
        builder.append(firstSignin);
        builder.append(", roles=");
        builder.append(roles);
        builder.append("]");
        return builder.toString();
    }

}
