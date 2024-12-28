package com.TTLTTBDD.server.controllers;

import com.TTLTTBDD.server.models.dto.CartDetailDTO;
import com.TTLTTBDD.server.models.entity.CartDetail;
import com.TTLTTBDD.server.services.CartDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart-details")
@CrossOrigin(origins = "http://localhost:31415")
public class CartDetailController {
    @Autowired
    private CartDetailService cartDetailService;

    @PostMapping("/add")
    public ResponseEntity<CartDetailDTO> addProductToCartDetail(
            @RequestParam Integer idProduct,
            @RequestParam Integer idUser) {
        CartDetailDTO cartDetailDTO = cartDetailService.addProductToCartDetail(idUser, idProduct);

        return ResponseEntity.ok(cartDetailDTO);
    }
}
