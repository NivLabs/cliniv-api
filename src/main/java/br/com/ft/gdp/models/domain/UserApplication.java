package br.com.ft.gdp.models.domain;

import java.util.ArrayList;
import java.util.Date;
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

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.ft.gdp.models.BaseObject;
import br.com.ft.gdp.models.dto.AddressDTO;
import br.com.ft.gdp.models.dto.DocumentDTO;
import br.com.ft.gdp.models.dto.UserInfoDTO;
import br.com.ft.gdp.models.enums.DocumentType;
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

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private Person person;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "USUARIO")
    private String username;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "DATA_NASCIMENTO")
    private Date bornDate;

    @Column(name = "NOME_MAE")
    private String motherName;

    @JsonIgnore
    @Column(name = "SENHA", nullable = false, length = 500)
    private String password;

    @Column(name = "ATIVO")
    private boolean active;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USUARIO_PERMISSAO", joinColumns = {@JoinColumn(name = "ID_USUARIO")}, inverseJoinColumns = {@JoinColumn(name = "ID_PERMISSAO")})
    private List<Role> roles = new ArrayList<>();

    @JsonIgnore
    public UserInfoDTO getUserInfoDTO() {
        UserInfoDTO userInfo = new UserInfoDTO();

        BeanUtils.copyProperties(this, userInfo);
        BeanUtils.copyProperties(this.getPerson(), userInfo, "user");
        if (!this.getPerson().getListOfAddress().isEmpty()) {
            AddressDTO address = new AddressDTO();
            BeanUtils.copyProperties(this.getPerson().getListOfAddress().get(0), address);
            userInfo.setAddress(address);
        }
        userInfo.setDocument(new DocumentDTO(DocumentType.CPF, person.getCpf()));
        
        return userInfo;
    }

}
