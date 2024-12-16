package com.TTLTTBDD.server.models.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cart_detail")
public class CartDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cart_detail")
    private int id_cart_detail;

    @Column(name = "id_cart", nullable = false)
    private int id_cart;

    @Column(name = "id_product", nullable = false)
    private int id_product;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    // Getter và Setter


    public int getId_cart_detail() {
        return id_cart_detail;
    }

    public void setId_cart_detail(int id_cart_detail) {
        this.id_cart_detail = id_cart_detail;
    }

    public int getId_cart() {
        return id_cart;
    }

    public void setId_cart(int id_cart) {
        this.id_cart = id_cart;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}