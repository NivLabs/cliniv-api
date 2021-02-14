package br.com.nivlabs.gp.models.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Esta classe representa as informações detalhadas do usuário, serve para criação e atualização das informações do usuário
 * 
 * @author viniciosarodrigues
 *
 */
@ApiModel(description = "Informações detalhadas do usuário")
public class UserInfoDTO extends PersonInfoDTO {

    private static final long serialVersionUID = 2270108536170182840L;

    @ApiModelProperty("Nome de usuário")
    @NotNull(message = "Informe o nome de usuário")
    @Size(max = 80, min = 2, message = "O nome de usuário deve conter de 2 a 80 caracteres")
    private String userName;

    @ApiModelProperty("Data de criação do usuário")
    private LocalDateTime createdAt;

    @ApiModelProperty("Papéis do usuário")
    private List<RoleDTO> roles = new ArrayList<>();

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDTO> roles) {
        this.roles = roles;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
        result = prime * result + ((roles == null) ? 0 : roles.hashCode());
        result = prime * result + ((userName == null) ? 0 : userName.hashCode());
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
        if (createdAt == null) {
            if (other.createdAt != null)
                return false;
        } else if (!createdAt.equals(other.createdAt))
            return false;
        if (roles == null) {
            if (other.roles != null)
                return false;
        } else if (!roles.equals(other.roles))
            return false;
        if (userName == null) {
            if (other.userName != null)
                return false;
        } else if (!userName.equals(other.userName))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "UserInfoDTO [userName=" + userName + ", createdAt=" + createdAt + ", roles=" + roles + "]";
    }

}
