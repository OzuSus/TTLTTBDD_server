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

    public void addProductToCart(Integer idUser, Integer idProduct) {
        Cart cart = cartRepository.findByIdUser(idUser);
        if (cart == null) {
            cart = new Cart();
            cart.setIdUser(idUser);
            cart = cartRepository.save(cart);
        }

        CartDetail cartDetail = new CartDetail();
        cartDetail.setCart(cart);
        cartDetail.setIdProduct(idProduct);
        cartDetailRepository.save(cartDetail);
    }

    public void removeProductFromCart(Integer idCartDetail) {
        cartDetailRepository.deleteById(idCartDetail);
    }

    public List<CartDetail> getCartDetailsByUser(Integer idUser) {
        Cart cart = cartRepository.findByIdUser(idUser);
        if (cart != null) {
            return cartDetailRepository.findByCart_IdCart(cart.getIdCart());
        }
        return List.of();
    }
}