package com.libertywallet.repository;


import com.libertywallet.entity.UserFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserFeedBackRepository  extends JpaRepository<UserFeedback , UUID> {
    Optional<UserFeedback> findByUserId(UUID userId);
    List<UserFeedback> findByUserIdAndFavoriteTrue(UUID userId);
    List<UserFeedback> findByUserIdAndLikedTrue(UUID userId);

}
