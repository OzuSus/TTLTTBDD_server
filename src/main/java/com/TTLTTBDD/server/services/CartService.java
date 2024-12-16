package com.TTLTTBDD.server.services;


import com.TTLTTBDD.server.models.entity.Cart;
import com.TTLTTBDD.server.models.entity.CartDetail;
import com.TTLTTBDD.server.repositories.CartDetailRepository;
import com.TTLTTBDD.server.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartDetailRepository cartDetailRepository;

    public void addProductToCart(int userId, int id_product, int quantity) {
        // Tìm giỏ hàng theo user
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    // Tạo giỏ hàng mới nếu chưa có
                    Cart newCart = new Cart();
                    newCart.setId_user(userId);
                    return cartRepository.save(newCart);
                });

        // Kiểm tra xem sản phẩm đã tồn tại trong giỏ hàng hay chưa
        CartDetail cartDetail = cartDetailRepository.findByCartIdAndProductId(cart.getId_cart(), id_product)
                .orElseGet(() -> {
                    CartDetail newCartDetail = new CartDetail();
                    newCartDetail.setId_cart(cart.getId_cart());
                    newCartDetail.setId_product(id_product);
                    newCartDetail.setQuantity(0);
                    return newCartDetail;
                });

        // Cập nhật số lượng
        cartDetail.setQuantity(cartDetail.getQuantity() + quantity);
        cartDetailRepository.save(cartDetail);
    }

    public void removeProductFromCart(int userId, int productId) {
        // Tìm giỏ hàng theo user
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user id: " + userId));

        // Xóa sản phẩm khỏi giỏ hàng
        cartDetailRepository.deleteByCartIdAndProductId(cart.getId_cart(), productId);
    }

    public List<CartDetail> getAllProductsInCart(int userId) {
        // Tìm giỏ hàng theo user
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user id: " + userId));

        // Lấy tất cả sản phẩm trong giỏ hàng
        return cartDetailRepository.findByCartId(cart.getId_cart());
    }
}
