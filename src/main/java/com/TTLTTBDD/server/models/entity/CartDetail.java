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
    @Column(name = "id_cartdetail")
    private Integer idCartDetail;

    @ManyToOne
    @JoinColumn(name = "id_cart", nullable = false)
    private Cart cart;

    @Column(name = "id_product", nullable = false)
    private Integer idProduct;

    public Integer getIdCartDetail() {
        return idCartDetail;
    }

    public void setIdCartDetail(Integer idCartDetail) {
        this.idCartDetail = idCartDetail;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Integer getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }
}


