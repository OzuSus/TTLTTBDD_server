package com.TTLTTBDD.server.repositories;

import com.TTLTTBDD.server.models.entity.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartDetailRepository extends JpaRepository<CartDetail, Integer> {
}
