package com.fluent.controller;

import com.fluent.dto.FavoriteDTO;
import com.fluent.security.JwtUtil;
import com.fluent.service.favorite.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {
    private final FavoriteService favoriteService;
    private final JwtUtil jwtUtil;

    @Autowired
    public FavoriteController(FavoriteService favoriteService, JwtUtil jwtUtil) {
        this.favoriteService = favoriteService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public ResponseEntity<List<FavoriteDTO>> getFavoritesByEmail(@RequestHeader("Authorization") String token) {
        String email = jwtUtil.extractEmail(token.substring(7)); // "Bearer " 제거
        List<FavoriteDTO> favorites = favoriteService.findFavoritesByEmail(email);
        if (favorites.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(favorites);
    }

    @PostMapping
    public ResponseEntity<FavoriteDTO> createFavorite(@RequestHeader("Authorization") String token, @RequestBody FavoriteDTO favoriteDTO) {
        String email = jwtUtil.extractEmail(token.substring(7));
        favoriteDTO.setMemberId(email);
        FavoriteDTO savedFavorite = favoriteService.saveFavorite(favoriteDTO);
        return ResponseEntity.ok(savedFavorite);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFavorite(@PathVariable Long id) {
        favoriteService.deleteFavorite(id);
        return ResponseEntity.ok().build();
    }
}