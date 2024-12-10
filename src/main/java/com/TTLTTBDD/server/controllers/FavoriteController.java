package com.TTLTTBDD.server.controllers;

import com.TTLTTBDD.server.models.dto.ProductDTO;
import com.TTLTTBDD.server.services.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {
    @Autowired
    private FavoriteService favoriteService;

    @PostMapping("/add")
    public ResponseEntity<String> addFavorite(@RequestParam Integer userId, @RequestParam Integer productId) {
        String result = favoriteService.addFavorite(userId, productId);
        if (result.contains("không tồn tại")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> removeFavoriteByUserIdAndProductId(@RequestParam Integer userId, @RequestParam Integer productId) {
        String result = favoriteService.removeFavoriteByUserIdAndProductId(userId, productId);
        if (result.contains("không tồn tại")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }



}
