package com.TTLTTBDD.server.repositories;

import com.TTLTTBDD.server.models.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {
}
