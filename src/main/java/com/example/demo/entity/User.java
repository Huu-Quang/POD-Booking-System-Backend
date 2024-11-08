package com.example.demo.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    long id;

    @JsonIgnore
    boolean isDeleted = false;


    @NotBlank(message = "Name can not be blank!")
    String name;


    @Min(value = 0, message = "Age can not be less than 0!")
    int age;


    @Pattern(regexp = "(84|0[3|5|7|8|9])+(\\d{8})\\b", message = "Phone number is invalid")
    String phone;


    @Email(message = "Invalid email")
    String email;

    @ManyToOne
    @JoinColumn(name = "account_id")
            @JsonIgnore
    Account account;


}
