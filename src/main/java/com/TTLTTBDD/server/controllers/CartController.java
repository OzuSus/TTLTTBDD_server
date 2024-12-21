package com.TTLTTBDD.server.controllers;

import com.TTLTTBDD.server.models.dto.AddToCartRequest;
import com.TTLTTBDD.server.models.entity.CartDetail;
import com.TTLTTBDD.server.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:31415")
public class CartController {

    @Autowired
    private CartService cartService;

    // Thêm sản phẩm vào giỏ hàng
    @PostMapping
    public String addProductToCart(@RequestBody AddToCartRequest request) {
        cartService.addProductToCart(request.getIdUser(), request.getIdProduct());
        return "Product added to cart successfully!";
    }

    // Xóa sản phẩm khỏi giỏ hàng
    @DeleteMapping("/{idCartDetail}")
    public String removeProductFromCart(@PathVariable Integer idCartDetail) {
        cartService.removeProductFromCart(idCartDetail);
        return "Product removed from cart successfully!";
    }

    // Lấy danh sách sản phẩm trong giỏ hàng
    @GetMapping("/{idUser}")
    public List<CartDetail> getCartDetailsByUser(@PathVariable Integer idUser) {
        return cartService.getCartDetailsByUser(idUser);
    }
}

