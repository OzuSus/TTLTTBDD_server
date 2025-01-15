package com.TTLTTBDD.server.controllers;

import com.TTLTTBDD.server.models.dto.OderDetailDTO;
import com.TTLTTBDD.server.models.dto.OrderDTO;
import com.TTLTTBDD.server.services.OderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByUserId(@PathVariable int userId) {
        List<OrderDTO> orders = oderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }
    @GetMapping("/orderDetails/{orderId}")
    public ResponseEntity<List<OderDetailDTO>> getOrderDetailsByOrderId(@PathVariable int orderId) {
        List<OderDetailDTO> orders = oderService.getOrderDetailsByIdOder_Id(orderId);
        return ResponseEntity.ok(orders);
    }
}

