package com.example.demo.controller;

import com.example.demo.entity.PODBooking;
import com.example.demo.model.PODBookingRequest;
import com.example.demo.model.PODBookingResponse;
import com.example.demo.service.PODBookingService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/PODBooking")
@SecurityRequirement(name = "api")
@CrossOrigin(origins = "*")
public class PODBookingAPI {
    @Autowired
    PODBookingService podBookingService;

    @PostMapping("/add")
    public ResponseEntity<PODBooking> creatBooking(@RequestBody PODBookingRequest podBookingRequest){
        try {
            // Parse start and end times
            // Call the service
            PODBooking booking;
            if(podBookingRequest.getBookId() !=null){
                booking = podBookingService.createNewSlot(LocalDateTime.parse(podBookingRequest.getStart()) , LocalDateTime.parse(podBookingRequest.getEnd()),podBookingRequest.getBookId()).getBook();
            }
            else {
                booking = podBookingService.createBooking(LocalDateTime.parse(podBookingRequest.getStart()), LocalDateTime.parse(podBookingRequest.getEnd()));
            }
            return new ResponseEntity<>(booking, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/checkout/{id}")
    public ResponseEntity<PODBookingResponse> checkoutBooking(@PathVariable("id") String id){
        try {
            return new ResponseEntity<>(this.podBookingService.checkOut(Long.valueOf(id)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/pay/{id}")
    public ResponseEntity<PODBookingResponse> payBooking(@PathVariable("id") String id){
        try {
            return new ResponseEntity<>(this.podBookingService.pay(Long.valueOf(id)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
