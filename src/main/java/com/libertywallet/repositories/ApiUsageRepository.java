package com.libertywallet.repositories;

import com.libertywallet.models.ChatGptApiUsage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApiUsageRepository extends JpaRepository<ChatGptApiUsage,Long> {
    Optional<ChatGptApiUsage> findById(Long id);

}
