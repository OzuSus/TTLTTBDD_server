package com.TTLTTBDD.server.controllers;

import com.TTLTTBDD.server.services.OderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:31415")
public class OderController {

    @Autowired
    private OderService oderService;

    @PostMapping("/place")
    public ResponseEntity<?> placeOrder(
            @RequestParam Integer idUser,
            @RequestParam Integer idPaymentMethop) {
        try {
            oderService.placeOrder(idUser, idPaymentMethop);
            return ResponseEntity.ok("Đặt đơn thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // API: Lấy tất cả orders kèm tổng giá trị
    @GetMapping
    public ResponseEntity<?> getAllOrdersWithTotalPrice() {
        try {
            return ResponseEntity.ok(oderService.getAllOrdersWithTotalPrice());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // API: Thêm sản phẩm vào order
    @PutMapping("/{orderId}/add-product")
    public ResponseEntity<?> addProductToOrder(
            @PathVariable Integer orderId,
            @RequestParam Integer productId,
            @RequestParam Integer quantity) {
        try {
            oderService.addProductToOrder(orderId, productId, quantity);
            return ResponseEntity.ok("Thêm sản phẩm thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Thêm API này vào OderController
    @GetMapping("/latest-id")
    public ResponseEntity<?> getLastOrderId() {
        try {
            Integer lastOrderId = oderService.getLastOrderId();
            return ResponseEntity.ok(Map.of("orderId", lastOrderId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // API: Xóa sản phẩm khỏi order
    @PutMapping("/{orderId}/remove-product")
    public ResponseEntity<?> removeProductFromOrder(
            @PathVariable Integer orderId,
            @RequestParam Integer productId) {
        try {
            oderService.removeProductFromOrder(orderId, productId);
            return ResponseEntity.ok("Xóa sản phẩm thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // API: Cập nhật trạng thái order
    @PutMapping("/{orderId}/update-status")
    public ResponseEntity<?> updateOrderStatus(
            @PathVariable Integer orderId,
            @RequestParam Integer statusId) {
        try {
            oderService.updateOrderStatus(orderId, statusId);
            return ResponseEntity.ok("Cập nhật trạng thái thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // API: Tăng/giảm số lượng sản phẩm trong order
    @PutMapping("/{orderId}/update-quantity")
    public ResponseEntity<?> updateProductQuantity(
            @PathVariable Integer orderId,
            @RequestParam Integer productId,
            @RequestParam Integer quantity) {
        try {
            oderService.updateProductQuantity(orderId, productId, quantity);
            return ResponseEntity.ok("Cập nhật số lượng thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // API: Xóa order
    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable Integer orderId) {
        try {
            oderService.deleteOrder(orderId);
            return ResponseEntity.ok("Xóa đơn hàng thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{orderId}/details")
    public ResponseEntity<?> getAllOrderDetails(@PathVariable Integer orderId) {
        try {
            return ResponseEntity.ok(oderService.getAllOrderDetailsByOrderId(orderId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
