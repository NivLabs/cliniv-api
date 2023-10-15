package br.com.nivlabs.cliniv.service.sticker;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nivlabs.cliniv.models.dto.StickerDTO;
import br.com.nivlabs.cliniv.service.BaseService;
import br.com.nivlabs.cliniv.service.sticker.business.CreateStickersBusinessHandler;
import br.com.nivlabs.cliniv.service.sticker.business.DeleteStickerBusinessHandler;
import br.com.nivlabs.cliniv.service.sticker.business.SearchStickersBusinessHandler;
import br.com.nivlabs.cliniv.service.sticker.business.UpdateStickersBusinessHandler;

@Service
public class StickerService implements BaseService{

    @Autowired
    private SearchStickersBusinessHandler searchStickersBusinessHandler;
    @Autowired
    private CreateStickersBusinessHandler createStickersBusinessHandler;
    @Autowired
    private UpdateStickersBusinessHandler updateStickersBusinessHandler;
    @Autowired
    private DeleteStickerBusinessHandler deleteStickerBusinessHandler;

    /**
     * Consulta todos os lembretes do usuário logado
     * 
     * @return Lista de lembretes do usuário logado
     */
    public List<StickerDTO> getAll() {
        return searchStickersBusinessHandler.getAll();
    }

    /**
     * Realiza o cadastro de um novo lembrete
     * 
     * @param request Requisição de criação de um novo lembrete
     * @return Lembrete criado
     */
    public StickerDTO create(StickerDTO request) {
        return createStickersBusinessHandler.execute(request);
    }

    /**
     * Realiza a atualiação de um cadastro de um lembrete já existente
     * 
     * @param id Identificador único do lembrete
     * @param request Requisição de atualização de um lembrete existente
     * @return Lembrete atualizado
     */
    public StickerDTO update(Long id, StickerDTO request) {
        request.setId(id);
        return updateStickersBusinessHandler.execute(request);
    }

    /**
     * Realiza a exclusão de um lembrete existente
     * 
     * @param id Identificador único do lembrete
     */
    public void delete(Long id) {
        deleteStickerBusinessHandler.execute(id);
    }
}
