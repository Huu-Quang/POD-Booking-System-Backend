package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;


    String image;


    @NotBlank(message = "Name is mandatory")
    String name;


    String description;


    String price;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    List<OrderDetail> orderDetails;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonIgnore
    Account account;


}
