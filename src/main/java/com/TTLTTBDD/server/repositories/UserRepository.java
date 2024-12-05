package com.TTLTTBDD.server.repositories;

import com.TTLTTBDD.server.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsernameAndPassword(String username, String password);
    Optional<User> findUsersById(int id);

    Optional<User> findByUsernameAndEmail(String username, String email);
}
