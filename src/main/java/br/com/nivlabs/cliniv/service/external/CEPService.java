package br.com.nivlabs.cliniv.service.external;

import org.springframework.stereotype.Service;

import br.com.nivlabs.cliniv.integration.cep.viacep.ViaCepAddress;
import br.com.nivlabs.cliniv.integration.cep.viacep.ViaCepClient;
import br.com.nivlabs.cliniv.models.dto.AddressDTO;

@Service
public class CEPService {

    public AddressDTO get(String cep) {
        return getByViaCep(cep);
    }

    private AddressDTO getByViaCep(String cep) {
        ViaCepAddress address = new ViaCepClient().getAddressByCep(cep);
        AddressDTO response = new AddressDTO();
        response.setCity(address.getLocalidade());
        response.setNeighborhood(address.getBairro());
        response.setPostalCode(cep);
        response.setState(address.getUf());
        response.setStreet(address.getLogradouro());
        return response;
    }
}
