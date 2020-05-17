package br.com.tl.gdp.rest.client.cep.viacep;

import br.com.tl.gdp.rest.client.RestClientObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Objeto de endereço da Via Cep
 * 
 * @author viniciosarodrigues
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class ViaCepAddress extends RestClientObject {

    private static final long serialVersionUID = 3089097894140675162L;

    private String cep;
    private String logradouro;
    private String complement;
    private String bairro;
    private String localidade;
    private String uf;
    private String unidade;
    private String ibge;
    private String gia;
}
