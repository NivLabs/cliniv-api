package br.com.ft.gdp.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ErroPadraoSpring {

    private Long timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

}
