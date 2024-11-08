package com.example.demo.service;

import com.example.demo.entity.Account;
import com.example.demo.entity.CoffeeShop;
import com.example.demo.entity.Feedback;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.model.Request.FeedbackRequest;
import com.example.demo.repository.CoffeeShopRepository;
import com.example.demo.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {
    @Autowired
    FeedbackRepository feedbackRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    CoffeeShopRepository coffeeShopRepository;

    public Feedback createFeedback(FeedbackRequest feedbackRequest) {
        Account user = authenticationService.getCurrentAccount();
        CoffeeShop coffeeShop = coffeeShopRepository.findCoffeeShopById(feedbackRequest.getShopId());
        Feedback feedback = new Feedback();
        feedback.setUser(user);
        feedback.setCoffeeShop(coffeeShop);
        feedback.setRating(feedbackRequest.getRating());
        feedback.setContent(feedbackRequest.getContent());
        return feedbackRepository.save(feedback);
    }

    public Feedback updateFeedback(FeedbackRequest feedbackRequest) {
        Feedback feedback = feedbackRepository.findById(feedbackRequest.getShopId())
                .orElseThrow(() -> new EntityNotFoundException("Feedback not found"));
        feedback.setContent(feedbackRequest.getContent());
        feedback.setRating(feedbackRequest.getRating());
        return feedbackRepository.save(feedback);
    }

    public void deleteFeedback(Long feedbackId) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new EntityNotFoundException("Feedback not found"));
        feedbackRepository.delete(feedback);
    }

    public List<Feedback> getFeedback() {
        return feedbackRepository.findAll();
    }
    public List<Feedback> findFeedbacksByRating(int rating) {
        return feedbackRepository.findAllByRating(rating);
    }

}
