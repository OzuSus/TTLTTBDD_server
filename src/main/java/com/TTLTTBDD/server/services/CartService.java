package com.TTLTTBDD.server.services;

import com.TTLTTBDD.server.models.entity.Cart;
import com.TTLTTBDD.server.models.entity.CartDetail;
import com.TTLTTBDD.server.models.entity.Product;
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

    //Lay gio hang cua nguoi dung
    public Cart getCartByUser(Integer userId){
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user " + userId));
    }
    //Them san pham vao gio hang
    public CartDetail addProductToCart(Integer userId, Integer productId){
        Cart cart = getCartByUser(userId);

        CartDetail cartDetail = cartDetailRepository.findByCartIdAndProductId(cart.getIdCart(),productId)
                .orElse(new CartDetail());
        cartDetail.setIdCart(cart);
        cartDetail.setIdProduct(new Product());
        return cartDetailRepository.save(cartDetail);
    }
    //Xoa san pham khoi gio hang
    public void removeProductFromCart(Integer userId, Integer productId){
        Cart cart = getCartByUser(userId);

        CartDetail cartDetail = cartDetailRepository.findByCartIdAndProductId(cart.getIdCart(),productId)
                .orElseThrow(() -> new RuntimeException("Product not found in cart " + userId));

        cartDetailRepository.delete(cartDetail);
    }
    //Lay san pham trong gio hang
    public List<CartDetail> getCartDetail(Integer userId){
        Cart cart = getCartByUser(userId);
        return cartDetailRepository.findByCartId(cart.getIdCart());
    }

}
