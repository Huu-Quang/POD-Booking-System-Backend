package com.example.demo.controller;

import com.example.demo.entity.Account;
import com.example.demo.entity.Orders;
import com.example.demo.model.Request.OrderRequest;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@SecurityRequirement(name = "api")
@CrossOrigin(origins = "*")
public class OrderAPI {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    AuthenticationService authenticationService;
    @PostMapping
    public ResponseEntity create(@RequestBody OrderRequest orderRequest) throws Exception {
        String vnPayURL  = orderService.createUrl(orderRequest);
        return ResponseEntity.ok(vnPayURL);
    }
    @GetMapping
    public ResponseEntity getAll(){
        Account account = authenticationService.getCurrentAccount();
        List<Orders> orders = orderRepository.findOrdersByCustomer(account);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("{id}")
    public ResponseEntity get(@PathVariable Long id){
        Orders orders = orderRepository.findOrdersById(id);
        return ResponseEntity.ok(orders);
    }
    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody OrderRequest orderRequest) {
        Orders orders = orderService.update(id, orderRequest);
        return ResponseEntity.ok(orders);
    }
    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id){
        orderService.delete(id);
    }

    @PostMapping("/transaction")
    public ResponseEntity createTransaction(@RequestParam Long orderId) {
        orderService.createTransaction(orderId);
        return ResponseEntity.ok("success");
    }
}
