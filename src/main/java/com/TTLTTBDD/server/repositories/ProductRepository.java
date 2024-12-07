package com.TTLTTBDD.server.repositories;

import aj.org.objectweb.asm.commons.Remapper;
import com.TTLTTBDD.server.models.entity.Category;
import com.TTLTTBDD.server.models.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findProductById(int id);
}
