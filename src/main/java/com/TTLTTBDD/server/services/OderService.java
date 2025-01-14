package com.TTLTTBDD.server.services;

import com.TTLTTBDD.server.models.entity.*;
import com.TTLTTBDD.server.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OderService {

    @Autowired
    private OderRepository oderRepository;

    @Autowired
    private OderDetailRepository oderDetailRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private UserRepository userRepository;  // Thêm repository User
    @Autowired
    private PaymentMethopRepository paymentMethopRepository;  // Thêm repository PaymentMethop

    // Lấy tất cả orders kèm tổng giá trị
    public List<Map<String, Object>> getAllOrdersWithTotalPrice() {
        List<Oder> orders = oderRepository.findAll();
        return orders.stream()
                .map(order -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("orderId", order.getId());
                    result.put("userId", order.getIdUser().getId());
                    result.put("dateOrder", order.getDateOrder());
                    result.put("paymentMethodId", order.getIdPaymentMethop().getId());
                    result.put("paymentMethodName", order.getIdPaymentMethop().getTypePayment());
                    result.put("statusId", order.getIdStatus().getId());
                    result.put("statusName", order.getIdStatus().getName());
                    result.put("totalPrice", calculateOrderTotalPrice(order.getId()));
                    return result;
                })
                .collect(Collectors.toList());
    }

    // Thêm sản phẩm vào order
    public void addProductToOrder(Integer orderId, Integer productId, Integer quantity) {
        Oder order = oderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order không tồn tại."));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Sản phẩm không tồn tại."));

        OderDetail existingDetail = oderDetailRepository.findAll()
                .stream()
                .filter(od -> od.getIdOder().getId().equals(orderId) && od.getIdProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existingDetail != null) {
            existingDetail.setQuantity(existingDetail.getQuantity() + quantity);
            existingDetail.setTotalprice(existingDetail.getTotalprice() + product.getPrize() * quantity);
            oderDetailRepository.save(existingDetail);
        } else {
            OderDetail newDetail = new OderDetail();
            newDetail.setIdOder(order);
            newDetail.setIdProduct(product);
            newDetail.setQuantity(quantity);
            newDetail.setTotalprice(product.getPrize() * quantity);
            oderDetailRepository.save(newDetail);
        }
    }

    // Xóa sản phẩm khỏi order
    public void removeProductFromOrder(Integer orderId, Integer productId) {
        OderDetail detail = oderDetailRepository.findAll()
                .stream()
                .filter(od -> od.getIdOder().getId().equals(orderId) && od.getIdProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Chi tiết sản phẩm không tồn tại trong order."));
        oderDetailRepository.delete(detail);
    }

    // Cập nhật trạng thái order
    public void updateOrderStatus(Integer orderId, Integer statusId) {
        Oder order = oderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order không tồn tại."));
        Status status = statusRepository.findById(statusId)
                .orElseThrow(() -> new IllegalArgumentException("Trạng thái không tồn tại."));
        order.setIdStatus(status);
        oderRepository.save(order);
    }

    // Tăng/giảm số lượng sản phẩm trong order
    public void updateProductQuantity(Integer orderId, Integer productId, Integer quantity) {
        OderDetail detail = oderDetailRepository.findAll()
                .stream()
                .filter(od -> od.getIdOder().getId().equals(orderId) && od.getIdProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Chi tiết sản phẩm không tồn tại trong order."));

        if (quantity <= 0) {
            throw new IllegalArgumentException("Số lượng phải lớn hơn 0.");
        }

        detail.setQuantity(quantity);
        detail.setTotalprice(detail.getIdProduct().getPrize() * quantity);
        oderDetailRepository.save(detail);
    }

    // Xóa order
    public void deleteOrder(Integer orderId) {
        Oder order = oderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order không tồn tại."));
        List<OderDetail> details = oderDetailRepository.findAll()
                .stream()
                .filter(od -> od.getIdOder().getId().equals(orderId))
                .collect(Collectors.toList());
        oderDetailRepository.deleteAll(details);
        oderRepository.delete(order);
    }

    // Tính tổng giá trị đơn hàng (không lưu vào DB)
    public BigDecimal calculateOrderTotalPrice(Integer orderId) {
        List<OderDetail> details = oderDetailRepository.findAll()
                .stream()
                .filter(od -> od.getIdOder().getId().equals(orderId))
                .toList();
        return details.stream()
                .map(detail -> BigDecimal.valueOf(detail.getTotalprice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Thêm mới một order
    public Integer placeOrder(Integer idUser, Integer idPaymentMethop, Integer productId, Integer quantity) {
        // Lấy thông tin người dùng và phương thức thanh toán từ cơ sở dữ liệu
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new IllegalArgumentException("User không tồn tại."));
        PaymentMethop paymentMethop = paymentMethopRepository.findById(idPaymentMethop)
                .orElseThrow(() -> new IllegalArgumentException("Phương thức thanh toán không tồn tại."));

        // Tạo đối tượng order mới
        Oder order = new Oder();
        order.setIdUser(user);
        order.setDateOrder(LocalDate.now()); // Ngày đặt hàng là ngày hiện tại
        order.setIdPaymentMethop(paymentMethop);
        order.setIdStatus(statusRepository.findById(5).orElseThrow(() -> new IllegalArgumentException("Trạng thái không hợp lệ"))); // Giả sử trạng thái mặc định là 1 (chưa xác nhận)

        // Lưu order vào cơ sở dữ liệu
        oderRepository.save(order);

       addProductToOrder(order.getId(), productId, quantity);
        return order.getId();
    }

    public List<Map<String, Object>> getAllOrderDetailsByOrderId(Integer orderId) {
        // Kiểm tra xem order có tồn tại không
        Oder order = oderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order không tồn tại."));

        // Lấy danh sách chi tiết đơn hàng
        List<OderDetail> details = oderDetailRepository.findAll()
                .stream()
                .filter(od -> od.getIdOder().getId().equals(orderId))
                .toList();

        return details.stream()
                .map(detail -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("orderDetailId",detail.getId());result.put("orderId",order.getId());
                    result.put("productId",detail.getIdProduct().getId());
                    result.put("productName", detail.getIdProduct().getName());
                    result.put("quantity", detail.getQuantity());
                    result.put("unitPrice", BigDecimal.valueOf(detail.getIdProduct().getPrize()));
                    result.put("totalPrice", BigDecimal.valueOf(detail.getTotalprice()).toPlainString());
                    return result;
                })
                .collect(Collectors.toList());
    }
}
