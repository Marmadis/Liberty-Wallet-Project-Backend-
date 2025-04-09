package com.libertywallet.repository;


import com.libertywallet.entity.UserFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserFeedBackRepository  extends JpaRepository<UserFeedback ,Long> {
    Optional<UserFeedback> findByUserId(Long userId);
    List<UserFeedback> findByUserIdAndFavoriteTrue(Long userId);
    List<UserFeedback> findByUserIdAndLikedTrue(Long userId);

}
