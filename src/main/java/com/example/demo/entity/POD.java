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
public class POD {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    String image;
    String description;
    String price;
    String location;


    @OneToMany(mappedBy = "pod")
            @JsonIgnore
    List<Account> accounts;

    @OneToMany(mappedBy = "pod")
    @JsonIgnore
    List<PODOrderDetail> orderPlanDetails;

    @OneToMany(mappedBy = "pod")
            @JsonIgnore
    List<Payment> payments;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonIgnore
    Account account;




}
