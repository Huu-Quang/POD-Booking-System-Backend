package com.example.demo.entity;

import com.example.demo.entity.Enum.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class POD {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String image;
    String description;
    String price;




    @OneToMany(mappedBy = "pod")
            @JsonIgnore
    List<Account> accounts;



    @OneToMany(mappedBy = "pod")
            @JsonIgnore
    List<Payment> payments;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonIgnore
    Account account;

    @ManyToOne
    @JoinColumn(name = "coffeeshop_id")
    @JsonIgnore
    CoffeeShop coffeeShop;






}
