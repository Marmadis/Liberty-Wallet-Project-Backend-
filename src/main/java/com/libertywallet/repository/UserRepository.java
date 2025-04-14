package com.libertywallet.repository;

import com.libertywallet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findById(UUID userId);
    Optional<User> findByEmail(String email);
}

