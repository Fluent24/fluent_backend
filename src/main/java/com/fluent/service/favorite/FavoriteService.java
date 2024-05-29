package com.fluent.service.favorite;

import com.fluent.dto.FavoriteDTO;
import com.fluent.entity.Favorite;

import java.util.List;

public interface FavoriteService {
    List<FavoriteDTO> findFavoritesByEmail(String email);
    FavoriteDTO saveFavorite(FavoriteDTO favoriteDTO);
    void deleteFavorite(Long id);
}