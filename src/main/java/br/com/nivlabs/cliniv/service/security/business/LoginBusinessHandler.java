package br.com.nivlabs.cliniv.service.security.business;

import br.com.nivlabs.cliniv.ApplicationMain;
import br.com.nivlabs.cliniv.config.security.JwtUtils;
import br.com.nivlabs.cliniv.config.security.UserOfSystem;
import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.UserApplication;
import br.com.nivlabs.cliniv.models.dto.CredentialsDTO;
import br.com.nivlabs.cliniv.repository.UserRepository;
import br.com.nivlabs.cliniv.service.BaseBusinessHandler;
import br.com.nivlabs.cliniv.util.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Componente de operações de login
 *
 * @author viniciosarodrigues
 */
@Component
public class LoginBusinessHandler implements BaseBusinessHandler {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userDao;
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private Logger logger;

    @Transactional
    public String login(CredentialsDTO credentials, String customerId) {
        logger.info("Iniciando processo de autenticação do usuário :: {} | {}", credentials.getUsername(), credentials.getEmail());
        if (StringUtils.isNullOrEmpty(customerId)) {
            throw new HttpException(HttpStatus.UNAUTHORIZED, "Cabeçalho de identificação do cliente não enviado");
        }
        UserApplication user = userDao.findByUserName(credentials.getUsername()).orElseThrow(() -> new HttpException(HttpStatus.UNAUTHORIZED, "Usuário e/ou senha inválidos!"));
        boolean isExpired = !user.isActive();

        if (!encoder.matches(credentials.getPassword().trim(), user.getPassword())) {
            throw new HttpException(HttpStatus.UNAUTHORIZED, "Usuário e/ou senha inválidos!");
        }

        logger.info("Login bem sucedido, realizando atualização da coluna de último acesso (ULTIMO_ACESSO) no banco...");
        user.setLastAcess(LocalDateTime.now(ZoneId.of(ApplicationMain.AMERICA_SAO_PAULO)));
        userDao.save(user);

        return jwtUtils.generateToken(new UserOfSystem(user.getUserName(), user.getPassword(), user.getPerson(), isExpired, user.getRoles(), customerId));
    }
}
