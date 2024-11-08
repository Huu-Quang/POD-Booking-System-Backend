package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class PODOrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    int quantity;
    float price;
    float total;

    @ManyToOne
    @JoinColumn(name = "pod_order_id")
    @JsonIgnore
    PODOrder podOrder;

    @ManyToOne
    @JoinColumn(name = "pod_id")
    @JsonIgnore
    POD pod;

}
