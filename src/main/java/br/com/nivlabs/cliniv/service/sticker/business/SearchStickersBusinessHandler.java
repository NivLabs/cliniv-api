package br.com.nivlabs.cliniv.service.sticker.business;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.nivlabs.cliniv.models.domain.Sticker;
import br.com.nivlabs.cliniv.models.domain.UserApplication;
import br.com.nivlabs.cliniv.models.dto.StickerDTO;
import br.com.nivlabs.cliniv.repository.StickerRepository;
import br.com.nivlabs.cliniv.service.BaseBusinessHandler;
import br.com.nivlabs.cliniv.service.userservice.UserService;
import br.com.nivlabs.cliniv.util.SecurityContextUtil;

@Component
public class SearchStickersBusinessHandler implements BaseBusinessHandler {

    @Autowired
    private Logger logger;

    @Autowired
    private StickerRepository repo;
    @Autowired
    private UserService userService;

    /**
     * Consulta todos os lembretes do usuário logado
     * 
     * @return Lista de lembretes do usuário logado
     */
    public List<StickerDTO> getAll() {
        List<StickerDTO> stickers = new ArrayList<StickerDTO>();
        logger.info("Iniciando processo de consulta de lembretes por usuário...");
        final String userName = SecurityContextUtil.getAuthenticatedUser().getUsername();
        final Long userId = userService.findByUserName(userName).getId();

        logger.info("Buscando lembretes do usuário logado {} | ID {}...", userName, userId);
        List<Sticker> stickersFromDB = repo.findByUser(new UserApplication(userId));
        if (stickersFromDB != null) {
            stickersFromDB.forEach(entity -> {
                stickers.add(new StickerDTO(entity.getId(), entity.getCreatedAt(), entity.getDescription()));
            });
        }
        return stickers;
    }
}
