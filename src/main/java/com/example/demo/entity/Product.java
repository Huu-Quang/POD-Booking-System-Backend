package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
    String name;
    String description;
    String price;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    List<OrderDetail> orderDetails;


}
