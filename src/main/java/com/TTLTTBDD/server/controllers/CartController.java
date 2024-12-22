package com.TTLTTBDD.server.controllers;

import com.TTLTTBDD.server.models.entity.CartDetail;
import com.TTLTTBDD.server.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:31415")
public class CartController {

    @Autowired
    private CartService cartService;

    // API thêm sản phẩm trong giỏ hàng của người dùng
    @GetMapping("/{userId}")
    public List<CartDetail> getCartDetail(@PathVariable int userId) {
        return cartService.getCartDetail(userId);
    }
    // API thêm sản phẩm vào giỏ hàng
    @PostMapping("/{userId}/add")
    public CartDetail addProductToCart(@PathVariable int userId, @RequestParam int productId) {
        return cartService.addProductToCart(userId, productId);
    }
    // API xóa sản phẩm khỏi giỏ hàng
    @DeleteMapping("/{userId}/remove")
    public void removeProductFromCart(@PathVariable int userId, @RequestParam int productId) {
        cartService.removeProductFromCart(userId, productId);
    }


}
