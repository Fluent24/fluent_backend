package com.fluent.service.favorite;

import com.fluent.dto.FavoriteDTO;
import com.fluent.entity.Favorite;
import com.fluent.mapper.EntityMapper;
import com.fluent.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteRepository favoriteRepository;

    public FavoriteServiceImpl(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    @Override
    public List<FavoriteDTO> findFavoritesByEmail(String email) {
        return favoriteRepository.findByMember_Email(email).stream()
                                 .map(EntityMapper.INSTANCE::favoriteToFavoriteDTO)
                                 .collect(Collectors.toList());
    }

    @Override
    public FavoriteDTO saveFavorite(FavoriteDTO favoriteDTO) {
        Favorite favorite = EntityMapper.INSTANCE.favoriteDTOToFavorite(favoriteDTO);
        Favorite savedFavorite = favoriteRepository.save(favorite);
        return EntityMapper.INSTANCE.favoriteToFavoriteDTO(savedFavorite);
    }

    @Override
    public void deleteFavorite(Long id) {
        favoriteRepository.deleteById(id);
    }
}
