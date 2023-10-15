package br.com.nivlabs.cliniv.service.sticker.business;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.nivlabs.cliniv.ApplicationMain;
import br.com.nivlabs.cliniv.models.domain.Sticker;
import br.com.nivlabs.cliniv.models.domain.UserApplication;
import br.com.nivlabs.cliniv.models.dto.StickerDTO;
import br.com.nivlabs.cliniv.repository.StickerRepository;
import br.com.nivlabs.cliniv.service.BaseBusinessHandler;
import br.com.nivlabs.cliniv.service.userservice.UserService;
import br.com.nivlabs.cliniv.util.SecurityContextUtil;

@Component
public class CreateStickersBusinessHandler implements BaseBusinessHandler {

    @Autowired
    private Logger logger;

    @Autowired
    private StickerRepository repo;
    @Autowired
    private UserService userService;

    public StickerDTO execute(StickerDTO newSticker) {
        logger.info("Iniciando processo de criação de lembrete por usuário...");
        final String userName = SecurityContextUtil.getAuthenticatedUser().getUsername();
        final Long userId = userService.findByUserName(userName).getId();

        Sticker newEntity = new Sticker();
        newEntity.setUser(new UserApplication(userId));
        newEntity.setDescription(newSticker.getDescription());
        if (newSticker.getCreatedAt() != null) {
            newEntity.setCreatedAt(newSticker.getCreatedAt());
        } else {
            newEntity.setCreatedAt(LocalDateTime.now(ZoneId.of(ApplicationMain.AMERICA_SAO_PAULO)));
        }

        repo.saveAndFlush(newEntity);
        newSticker.setId(newEntity.getId());
        logger.info("Lembrete criado com sucesso :: ID {} | USUARIO {}", newSticker.getId(), userName);
        return newSticker;
    }
}
