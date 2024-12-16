package com.TTLTTBDD.server.controllers;


import com.TTLTTBDD.server.models.entity.CartDetail;
import com.TTLTTBDD.server.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:31415")
@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // Thêm sản phẩm vào giỏ hàng
    @PostMapping("/add")
    public ResponseEntity<String> addProductToCart(@RequestParam int id_user, @RequestParam int id_product, @RequestParam int quantity) {
        cartService.addProductToCart(id_user, id_product, quantity);
        return ResponseEntity.ok("Product added to cart successfully!");
    }

    // Xóa sản phẩm khỏi giỏ hàng
    @DeleteMapping("/remove")
    public ResponseEntity<String> removeProductFromCart(@RequestParam int id_user, @RequestParam int id_product) {
        cartService.removeProductFromCart(id_user, id_product);
        return ResponseEntity.ok("Product removed from cart successfully!");
    }

    // Lấy tất cả sản phẩm trong giỏ hàng
    @GetMapping("/all")
    public ResponseEntity<List<CartDetail>> getAllProductsInCart(@RequestParam int id_user) {
        List<CartDetail> cartDetails = cartService.getAllProductsInCart(id_user);
        return ResponseEntity.ok(cartDetails);
    }
}
