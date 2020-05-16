package br.com.ft.gdp.models.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("License")
public class LicenseDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 5513329730873896353L;

    private String key;
    @DateTimeFormat(iso = ISO.DATE)
    private Date startDate;
    @DateTimeFormat(iso = ISO.DATE)
    private Date endDate;
}
