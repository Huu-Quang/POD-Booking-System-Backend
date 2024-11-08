package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class PODOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    Date date;

    float total;


    int quantity;


    float price;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    Account customer;

    @OneToMany(mappedBy = "podOrder", cascade = CascadeType.ALL)
    @JsonIgnore
    List<PODOrderDetail> podOrderDetails;

    @OneToOne(mappedBy = "podOrder")
    @JsonIgnore
    Payment payment;
    @ManyToOne
    @JoinColumn(name = "customer", nullable = false)
    Account account;

    @ManyToOne
    @JoinColumn(name = "pod_id", nullable = false)
    POD pod;
}
