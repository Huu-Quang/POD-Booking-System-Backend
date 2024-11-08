package com.example.demo.controller;

import com.example.demo.entity.Feedback;
import com.example.demo.model.Request.FeedbackRequest;
import com.example.demo.service.FeedbackService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedbacks")
@SecurityRequirement(name = "api")
@CrossOrigin(origins = "*")
public class FeedbackAPI {
    @Autowired
    FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity createFeedback(@RequestBody FeedbackRequest feedbackRequest) {
        Feedback feedback = feedbackService.createFeedback(feedbackRequest);
        return ResponseEntity.ok(feedback);
    }

    @GetMapping
    public ResponseEntity getFeedback() {
        List<Feedback> feedback = feedbackService.getFeedback();
        return ResponseEntity.ok(feedback);
    }

    @DeleteMapping
    public ResponseEntity deleteFeedback(@RequestParam Long feedbackId) {
        feedbackService.deleteFeedback(feedbackId);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity updateFeedback(@RequestBody FeedbackRequest feedbackRequest) {
        Feedback feedback = feedbackService.updateFeedback(feedbackRequest);
        return ResponseEntity.ok(feedback);
    }

    @GetMapping("/Id")
    public ResponseEntity<Feedback> getFeedbackById(@RequestParam Long id) {
        Feedback feedback = feedbackService.findFeedbackById(id);
        return ResponseEntity.ok(feedback);
    }

    @GetMapping("/searchByRating")
    public ResponseEntity<List<Feedback>> searchFeedbacksByRating(@RequestParam int rating) {
        List<Feedback> feedbacks = feedbackService.findFeedbacksByRating(rating);
        return ResponseEntity.ok(feedbacks);
    }

}
