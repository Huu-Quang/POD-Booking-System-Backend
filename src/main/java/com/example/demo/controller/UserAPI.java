package com.example.demo.controller;


import com.example.demo.entity.User;
import com.example.demo.model.UserRequest;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/user")
@CrossOrigin("http://localhost:3000")
@SecurityRequirement(name = "api")

public class UserAPI {

    //cors


    @Autowired
    UserService userService;

    @PostMapping
    @PreAuthorize("hasRole(ADMIN)")
    public ResponseEntity createUser(@Valid @RequestBody UserRequest user) {
        User newUser = userService.create(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity getAllUser() {
        List<User> users = userService.getAllUser();
        return ResponseEntity.ok(users);
    }

    // api/user/{UserId}
    @PutMapping("{Id}")
    public ResponseEntity updateUser(@PathVariable long Id, @Valid @RequestBody User user) {
        User updateUser = userService.update(Id, user);
        return ResponseEntity.ok(updateUser);
    }
    @DeleteMapping("{Id}")
    public ResponseEntity deleteUser(@PathVariable long Id){
        User deleteUser = userService.delete(Id);
        return ResponseEntity.ok(deleteUser);
    }


}