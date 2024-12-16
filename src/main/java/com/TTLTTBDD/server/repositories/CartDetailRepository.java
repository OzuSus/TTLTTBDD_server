package com.TTLTTBDD.server.repositories;



import com.TTLTTBDD.server.models.entity.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartDetailRepository extends JpaRepository<CartDetail, Integer> {
    Optional<CartDetail> findByCartIdAndProductId(int id, int product);

    void deleteByCartIdAndProductId(int id, int product);

    List<CartDetail> findByCartId(int id);
}
