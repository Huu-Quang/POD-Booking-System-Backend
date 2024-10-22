package com.example.demo.controller;


import com.example.demo.entity.User;
import com.example.demo.model.UserRequest;
import com.example.demo.model.UserResponse;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/user")
@CrossOrigin("*")
@SecurityRequirement(name = "api")

public class UserAPI {

    //cors

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity createUser(@Valid @RequestBody UserRequest userRequest) {
        User newUser = userService.create(userRequest);
        return ResponseEntity.ok(userRequest);
    }

    @GetMapping
    public ResponseEntity  getAllUsers(
            @RequestParam int page,
            @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "asc") String sortDir ) {
        UserResponse userResponse = userService.getAllUser(page, size, sortBy, sortDir);
        return ResponseEntity.ok(userResponse);
    }
    @GetMapping("{Id}")
    public ResponseEntity getUser(@PathVariable long Id) {
        User user = userService.getUser(Id);
        return ResponseEntity.ok(user);
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