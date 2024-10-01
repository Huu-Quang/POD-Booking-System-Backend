package com.example.demo.controller;


import com.example.demo.entity.Account;
import com.example.demo.model.AccountResponse;
import com.example.demo.model.LoginRequest;
import com.example.demo.model.RegisterRequest;
import com.example.demo.repository.AccountRepository;
import com.example.demo.service.AuthenticationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account")
@SecurityRequirement(name = "api")
public class AuthenticationAPI {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("register")

    public ResponseEntity register(@Valid @RequestBody RegisterRequest registerRequest) {
        AccountResponse newAccount = authenticationService.register(registerRequest);
        return ResponseEntity.ok("Account is created successfully");

    }

    @GetMapping
    public ResponseEntity getAllAccount() {
        List<Account> accounts = authenticationService.getAllAccount();
        return ResponseEntity.ok(accounts);
    }



    @PostMapping("login")
    public ResponseEntity login(@Valid @RequestBody LoginRequest loginRequest) {
        AccountResponse newAccount = authenticationService.login(loginRequest);
        return ResponseEntity.ok(newAccount);


    }
    @PutMapping("change-password/{Id}")
    public ResponseEntity<String> updateAccount(@PathVariable long Id, @RequestParam String oldPassword, String newPassword) {
        boolean change = authenticationService.changePassword(Id, oldPassword, newPassword);
        if (change) {
            return ResponseEntity.ok("change Password is successfully");
        } else {
            return ResponseEntity.status(400).body("change Password is not successfully");
        }
    }
}

