package com.TTLTTBDD.server.repositories;

import com.TTLTTBDD.server.models.entity.Oder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OderRepository extends JpaRepository<Oder, Integer> {
    List<Oder> findByIdUser_Id(int userId);

}