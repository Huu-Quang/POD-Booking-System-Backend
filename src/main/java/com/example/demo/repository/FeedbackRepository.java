package com.example.demo.repository;

import com.example.demo.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findAllByUserId(Long UserId);
    List<Feedback> findAllByRating(int rating);
    List<Feedback> findAllByCoffeeShopId(Long coffeeShopId);
}
