package com.TTLTTBDD.server.models.entity;

import jakarta.persistence.*;

@Entity
public class CartDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCartDetail;

    @ManyToOne
    @JoinColumn(name = "id_cart")
    private Cart idCart;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product idProduct;

    public CartDetail() {
    }

    public int getIdCartDetail() {
        return idCartDetail;
    }

    public void setIdCartDetail(int idCartDetail) {
        this.idCartDetail = idCartDetail;
    }

    public Cart getIdCart() {
        return idCart;
    }

    public void setIdCart(Cart idCart) {
        this.idCart = idCart;
    }

    public Product getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Product idProduct) {
        this.idProduct = idProduct;
    }
}