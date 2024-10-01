package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserRequest {
    @NotBlank(message = "Name can not be blank!")
    @Column(name = "name")
    String name;


    @Column(name = "Age")
    int age;



    @Pattern(regexp = "(84|0[3|5|7|8|9])+(d{8})", message = "Invalid phone")
    @Column(name = "Phone")
    String phone;



    @Column(name = "Email")
    String email;

}
