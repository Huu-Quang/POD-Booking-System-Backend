package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter

public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    Date date;

    float totalPrice;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    Account customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    List<OrderDetail> orderDetail;
}
