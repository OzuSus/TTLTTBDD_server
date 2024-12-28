package com.TTLTTBDD.server.services;


import com.TTLTTBDD.server.models.dto.CartDetailDTO;
import com.TTLTTBDD.server.models.entity.Cart;
import com.TTLTTBDD.server.models.entity.CartDetail;
import com.TTLTTBDD.server.models.entity.Product;
import com.TTLTTBDD.server.repositories.CartDetailRepository;
import com.TTLTTBDD.server.repositories.CartRepository;
import com.TTLTTBDD.server.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartDetailService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartDetailRepository cartDetailRepository;

    public CartDetailDTO addProductToCartDetail(Integer idUser, Integer idProduct) {
        Cart cart = cartRepository.findByIdUser_Id(idUser)
                .orElseThrow(() -> new IllegalArgumentException("Cart không tồn tại cho User này."));

        Product product = productRepository.findById(idProduct)
                .orElseThrow(() -> new IllegalArgumentException("Product không tồn tại."));

        CartDetail cartDetail = new CartDetail();
        cartDetail.setIdCart(cart);
        cartDetail.setIdProduct(product);

        CartDetail savedCartDetail = cartDetailRepository.save(cartDetail);

        return new CartDetailDTO(
                savedCartDetail.getId(),
                savedCartDetail.getIdCart().getId(),
                savedCartDetail.getIdProduct().getId()
        );
    }

    public void removeProductFromCartDetail(Integer idUser, Integer idProduct) {
        Cart cart = cartRepository.findByIdUser_Id(idUser)
                .orElseThrow(() -> new IllegalArgumentException("Cart không tồn tại cho User này."));

        CartDetail cartDetail = cartDetailRepository.findByIdCart_IdAndIdProduct_Id(cart.getId(), idProduct)
                .orElseThrow(() -> new IllegalArgumentException("CartDetail không tồn tại."));

        cartDetailRepository.delete(cartDetail);
    }
}

