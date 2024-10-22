package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserRequest {
    @NotBlank(message = "Name can not be blank!")
    String name;


    @Min(value = 0, message = "Age can not be less than 0!")
    int age;



    @Pattern(regexp = "(84|0[3|5|7|8|9])+(\\d{8})\\b", message = "Phone number is invalid")
    String phone;



    @Column(name = "Email")
    String email;

}
