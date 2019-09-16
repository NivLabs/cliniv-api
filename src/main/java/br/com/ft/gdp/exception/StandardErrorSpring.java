package br.com.ft.gdp.exception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.ft.gdp.models.exception.ErrorItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StandardErrorSpring implements Serializable {

    private static final long serialVersionUID = -2166865923471144622L;

    private Long timestamp;
    private Integer status;
    private String error;
    private List<ErrorItem> validations = new ArrayList<>();
    private String message;
    private String path;

}
