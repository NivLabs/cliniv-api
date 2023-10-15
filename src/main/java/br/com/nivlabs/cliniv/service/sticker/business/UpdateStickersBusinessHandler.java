package br.com.nivlabs.cliniv.service.sticker.business;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.cliniv.exception.HttpException;
import br.com.nivlabs.cliniv.models.domain.Sticker;
import br.com.nivlabs.cliniv.models.dto.StickerDTO;
import br.com.nivlabs.cliniv.repository.StickerRepository;
import br.com.nivlabs.cliniv.service.BaseBusinessHandler;
import br.com.nivlabs.cliniv.service.userservice.UserService;
import br.com.nivlabs.cliniv.util.SecurityContextUtil;

@Component
public class UpdateStickersBusinessHandler implements BaseBusinessHandler {

    @Autowired
    private Logger logger;

    @Autowired
    private StickerRepository repo;
    @Autowired
    private UserService userService;

    public StickerDTO execute(StickerDTO updatedSticker) {
        logger.info("Iniciando processo de criação de lembrete por usuário...");
        final String userName = SecurityContextUtil.getAuthenticatedUser().getUsername();
        final Long userId = userService.findByUserName(userName).getId();

        Sticker entity = repo.findById(updatedSticker.getId())
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND, "Lembrete não localizado"));

        if (!entity.getUser().getId().equals(userId)) {
            logger.warn("O lembrete foi encontrado mas não pertence ao usuário que está tentando atualizar.");
            throw new HttpException(HttpStatus.NOT_FOUND, "Lembrete não localizado");
        }
        entity.setDescription(updatedSticker.getDescription());

        entity = repo.saveAndFlush(entity);
        logger.info("Lembrete atualizado com sucesso :: ID {} | USUARIO {}", updatedSticker.getId(), userName);
        return updatedSticker;
    }
}
