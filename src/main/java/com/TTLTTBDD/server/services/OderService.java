package com.TTLTTBDD.server.services;

import com.TTLTTBDD.server.models.entity.*;
import com.TTLTTBDD.server.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class OderService {
    @Autowired
    private OderRepository oderRepository;
    @Autowired
    private OderDetailRepository oderDetailRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartDetailRepository cartDetailRepository;
    @Autowired
    private PaymentMethopRepository paymentMethopRepository;
    @Autowired
    private StatusRepository statusRepository;
    public void placeOrder(Integer idUser, Integer idPaymentMethop) {
        Cart cart = cartRepository.findByIdUser_Id(idUser)
                .orElseThrow(() -> new IllegalArgumentException("Cart không tồn tại cho User này."));

        List<CartDetail> cartDetails = cartDetailRepository.findAllByIdCart_Id(cart.getId());
        if (cartDetails.isEmpty()) {
            throw new IllegalArgumentException("Giỏ hàng trống, không thể đặt đơn.");
        }

        PaymentMethop paymentMethop = paymentMethopRepository.findById(idPaymentMethop)
                .orElseThrow(() -> new IllegalArgumentException("Phương thức thanh toán không hợp lệ."));
        Status status = statusRepository.findById(5)
                .orElseThrow(() -> new IllegalArgumentException("Trạng thái không tồn tại."));

        Oder oder = new Oder();
        oder.setIdUser(cart.getIdUser());
        oder.setDateOrder(LocalDate.now());
        oder.setIdPaymentMethop(paymentMethop);
        oder.setIdStatus(status);

        oderRepository.save(oder);

        for (CartDetail cartDetail : cartDetails) {
            Product product = cartDetail.getIdProduct();
            Integer cartQuantity = cartDetail.getQuantity();

            BigDecimal price = BigDecimal.valueOf(product.getPrize());
            BigDecimal totalPrice = price.multiply(BigDecimal.valueOf(cartQuantity));

            OderDetail oderDetail = new OderDetail();
            oderDetail.setIdOder(oder);
            oderDetail.setIdProduct(product);
            oderDetail.setQuantity(cartQuantity);
            oderDetail.setTotalprice(totalPrice.doubleValue());

            oderDetailRepository.save(oderDetail);
        }

        cartDetailRepository.deleteAll(cartDetails);
    }
}
