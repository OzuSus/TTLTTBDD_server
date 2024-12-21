package com.TTLTTBDD.server.repositories;

import com.TTLTTBDD.server.models.entity.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartDetailRepository extends JpaRepository<CartDetail, Integer> {
    List<CartDetail> findByCart_IdCart(Integer idCart);
}