package com.TTLTTBDD.server.services;

import com.TTLTTBDD.server.models.dto.*;
import com.TTLTTBDD.server.models.entity.*;
import com.TTLTTBDD.server.repositories.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<OrderDTO> getOrdersByUserId(int userId) {
        List<Oder> orders = oderRepository.findByIdUser_Id(userId);
        return orders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public List<OderDetailDTO> getOrderDetailsByIdOder_Id(int orderId){
        List<OderDetail> orders = oderDetailRepository.findByIdOder_Id(orderId);
        return orders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private OrderDTO convertToDTO(Oder oder) {
        StatusDTO statusDTO = StatusDTO.builder()
                .id(oder.getIdStatus().getId())
                .name(oder.getIdStatus().getName())
                .build();
        List<OderDetailDTO> oderDetailDTOList = oderDetailRepository.findByIdOder_Id(oder.getId()).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        System.out.println("Found " + oderDetailDTOList.size() + " OderDetail(s) for order ID " + oder.getId());

        return OrderDTO.builder()
                .idOrder(oder.getId())
                .userId(oder.getIdUser().getId())
                .dateOrder(oder.getDateOrder())
                .paymentMethodName(oder.getIdPaymentMethop().getTypePayment())
                .statusName(statusDTO)
                .orderDetails(oderDetailDTOList)
                .build();
    }
    private OderDetailDTO convertToDTO(OderDetail oder) {
        ProductDTO productDTO = ProductDTO.builder()
                .id(oder.getIdProduct().getId())
                .name(oder.getIdProduct().getName())
                .price(oder.getIdProduct().getPrize())
                .quantity(oder.getIdProduct().getQuantity())
                .image(oder.getIdProduct().getImage())
                .description(oder.getIdProduct().getDescription())
                .reviewCount(oder.getIdProduct().getReview())
                .rating(oder.getIdProduct().getRating())
                .categoryID(oder.getIdProduct().getIdCategory().getId())
                .build();
        return OderDetailDTO.builder()
                .id(oder.getId())
                .idOder(oder.getIdOder().getId())
                .idProduct(productDTO)
                .quantity(oder.getQuantity())
                .totalprice(oder.getTotalprice())
                .build();
    }
}
