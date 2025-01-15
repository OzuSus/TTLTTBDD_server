package com.TTLTTBDD.server.repositories;

import com.TTLTTBDD.server.models.entity.Oder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OderRepository extends JpaRepository<Oder, Integer> {
    Optional<Oder> findTopByOrderByIdDesc(); // Lấy đơn hàng có ID lớn nhất
}