package br.com.nivlabs.cliniv.service.security.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.nivlabs.cliniv.config.security.JwtUtils;
import br.com.nivlabs.cliniv.config.security.UserOfSystem;
import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.UserApplication;
import br.com.nivlabs.cliniv.models.dto.CredentialsDTO;
import br.com.nivlabs.cliniv.repository.UserRepository;
import br.com.nivlabs.cliniv.util.StringUtils;

/**
 * Componente de operações de login
 * 
 * @author viniciosarodrigues
 *
 */
@Component
public class LoginBusinessHandler {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserRepository userDao;
    @Autowired
    private JwtUtils jwtUtils;

    @Transactional
    public String login(CredentialsDTO credentials, String customerId) {
        if (StringUtils.isNullOrEmpty(customerId)) {
            throw new HttpException(HttpStatus.UNAUTHORIZED, "Cabeçalho de identificação do cliente não enviado");
        }
        UserApplication user = userDao.findByUserName(credentials.getUsername())
                .orElseThrow(() -> new HttpException(HttpStatus.UNAUTHORIZED, "Usuário e/ou senha inválidos!"));
        boolean isExpired = !user.isActive();

        if (!encoder.matches(credentials.getPassword().trim(), user.getPassword())) {
            throw new HttpException(HttpStatus.UNAUTHORIZED, "Usuário e/ou senha inválidos!");
        }
        return jwtUtils
                .generateToken(new UserOfSystem(user.getUserName(), user.getPassword(), user.getPerson(), isExpired, user.getRoles(),
                        customerId));
    }
}
