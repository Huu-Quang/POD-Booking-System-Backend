package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Feedback {
    @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String content;
    int rating;

    @ManyToOne
    @JoinColumn(name = "user_id")
            @JsonIgnore
    Account user;

    @ManyToOne
    @JoinColumn(name = "coffee_shop_id")
            @JsonIgnore
    CoffeeShop coffeeShop;
}
