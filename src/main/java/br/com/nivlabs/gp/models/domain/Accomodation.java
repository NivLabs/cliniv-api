package br.com.nivlabs.gp.models.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.nivlabs.gp.enums.AccomodationType;
import br.com.nivlabs.gp.models.BaseObjectWithId;
import br.com.nivlabs.gp.models.dto.AccomodationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SALA_LEITO")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Accomodation extends BaseObjectWithId {

    private static final long serialVersionUID = 7743590060203121165L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOME")
    private String description;

    @Column(name = "TIPO")
    @Enumerated(EnumType.STRING)
    private AccomodationType type;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "SECTOR_ID")
    private Sector sector;

    public AccomodationDTO getDTO() {
        AccomodationDTO dto = new AccomodationDTO();
        BeanUtils.copyProperties(this, dto);
        dto.setSectorId(this.getSector().getId());
        return dto;

    }
}
