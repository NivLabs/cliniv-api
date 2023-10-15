package br.com.nivlabs.cliniv.service.sticker.business;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.Sticker;
import br.com.nivlabs.cliniv.repository.StickerRepository;
import br.com.nivlabs.cliniv.service.BaseBusinessHandler;
import br.com.nivlabs.cliniv.service.userservice.UserService;
import br.com.nivlabs.cliniv.util.SecurityContextUtil;

@Component
public class DeleteStickerBusinessHandler implements BaseBusinessHandler {

    @Autowired
    private Logger logger;

    @Autowired
    private StickerRepository repo;
    @Autowired
    private UserService userService;

    public void execute(Long id) {
        logger.info("Iniciando processo de exclusão de lembrete por usuário...");
        final String userName = SecurityContextUtil.getAuthenticatedUser().getUsername();
        final Long userId = userService.findByUserName(userName).getId();

        Sticker entity = repo.findById(id).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND, "Lembrete não localizado."));
        if (!entity.getUser().getId().equals(userId)) {
            logger.warn("O lembrete foi encontrado mas não pertence ao usuário que está tentando atualizar.");
            throw new HttpException(HttpStatus.NOT_FOUND, "Lembrete não localizado");
        }
        repo.delete(entity);
    }

}
