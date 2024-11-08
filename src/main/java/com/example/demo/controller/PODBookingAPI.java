package com.example.demo.controller;

import com.example.demo.entity.PODBooking;
import com.example.demo.model.Request.PODBookingRequest;
import com.example.demo.repository.PODBookingRepository;
import com.example.demo.service.PODBookingService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/PODBooking")
@SecurityRequirement(name = "api")
@CrossOrigin(origins = "*")
public class PODBookingAPI {
    @Autowired
    PODBookingService podBookingService;

    @Autowired
    PODBookingRepository podBookingRepository;

    @PostMapping("/add")
    public ResponseEntity<String> createBooking(@RequestBody PODBookingRequest bookingRequest) {
        LocalDateTime start = LocalDateTime.parse(bookingRequest.getStart(), DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime end = LocalDateTime.parse(bookingRequest.getEnd(), DateTimeFormatter.ISO_DATE_TIME);
        String result = podBookingService.createBooking(start, end);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/checkout/{id}")
    public List<PODBooking> getAllBookingsByAccount(Long accountId) {
        return podBookingRepository.findByAccountId(accountId);
    }

    @GetMapping("/totalPrice/{accountId}")
    public ResponseEntity<Float> calculateTotalPrice(@PathVariable Long accountId) {
        float totalPrice = podBookingService.calculateTotalPrice(accountId);
        return new ResponseEntity<>(totalPrice, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateBooking(@PathVariable Long id, @RequestBody PODBookingRequest bookingRequest) {
        try {
            LocalDateTime start = LocalDateTime.parse(bookingRequest.getStart(), DateTimeFormatter.ISO_DATE_TIME);
            LocalDateTime end = LocalDateTime.parse(bookingRequest.getEnd(), DateTimeFormatter.ISO_DATE_TIME);
            String result = podBookingService.updateBooking(id, start, end);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable Long id) {
        try {
            podBookingService.deleteBooking(id);
            return new ResponseEntity<>("Booking deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/price/{bookingId}")
    public ResponseEntity<Float> getBookingId(@PathVariable Long bookingId) {
        try {
            float price = podBookingService.getBookingId(bookingId);
            return new ResponseEntity<>(price, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}