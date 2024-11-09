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


    @ManyToMany
    @JoinTable(
            name = "pod_booking",
            joinColumns = @JoinColumn(name = "pod_id"),
            inverseJoinColumns = @JoinColumn(name = "booking_id")
    )
    @JsonIgnore
    List<PODBooking> bookings;

    @OneToMany(mappedBy = "pod", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonIgnore
    List<PODBooking> podBookings;

    public Long getAccountId() {
        return account != null ? account.getId() : null;
    }








}