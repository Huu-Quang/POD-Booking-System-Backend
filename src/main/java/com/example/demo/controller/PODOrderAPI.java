package com.example.demo.controller;

import com.example.demo.entity.Account;
import com.example.demo.entity.PODOrder;
import com.example.demo.model.PODOrderRequest;
import com.example.demo.repository.PODRepository;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.PODOrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/PODOrders")
@SecurityRequirement(name = "api")
@CrossOrigin(origins = "*")
public class PODOrderAPI {
    @Autowired
    PODOrderService podOrderService;
    @Autowired
    PODRepository podRepository;
    @Autowired
    AuthenticationService   authenticationService;

    @PostMapping
    public ResponseEntity create(@RequestBody PODOrderRequest podOrderRequest) {
        PODOrder podOrder = podOrderService.create(podOrderRequest);
        return ResponseEntity.ok(podOrder);
    }
    @GetMapping
    public ResponseEntity getAll(){
        Account account = authenticationService.getCurrentAccount();
        List<PODOrder> podOrders = podOrderService.getAll();
        return ResponseEntity.ok(podOrders);
    }
    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable UUID id, @RequestBody PODOrderRequest podOrderRequest) {
        PODOrder podOrder = podOrderService.update(id, podOrderRequest);
        return ResponseEntity.ok(podOrder);
    }
    @DeleteMapping("{id}")
    public void delete(@PathVariable UUID id){
        podOrderService.delete(id);
    }
    @PostMapping("/transaction")
    public ResponseEntity createTransaction(@RequestParam UUID podOrderId) {
        podOrderService.createTransaction(podOrderId);
        return ResponseEntity.ok("Transaction created");
    }
}
