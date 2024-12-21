package com.TTLTTBDD.server.models.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddToCartRequest {
    private Integer idUser;
    private Integer idProduct;

}