package br.com.nivlabs.cliniv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nivlabs.cliniv.models.domain.Sticker;
import br.com.nivlabs.cliniv.models.domain.UserApplication;

@Repository
public interface StickerRepository extends JpaRepository<Sticker, Long> {

    public List<Sticker> findByUser(UserApplication userApplication);

}
