package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Role;

import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
public class CoffeeShop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotBlank(message = "Name can not be blank!")
    String name;

    @NotBlank(message = "Address can not be blank!")
    String address;

    @Pattern(regexp = "(84|0[3|5|7|8|9])+(\\d{8})", message = "Invalid phone")
    String phone;

    String image;

    @NotNull(message = "Open time can not be null!")
    String openTime;


    @NotNull(message = "Close time can not be null!")
    String closeTime;

    @OneToMany(mappedBy = "coffeeShop")
            @JsonIgnore
    List<POD> pods;

    @OneToMany(mappedBy = "coffeeShop")
    @JsonIgnore
    List<Feedback> feedbacks;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonIgnore
    Account account;


}
