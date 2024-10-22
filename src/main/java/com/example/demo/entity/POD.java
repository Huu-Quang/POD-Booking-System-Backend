package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class POD {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    long id;
    String image;
    String description;
    String price;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    Booking booking;

    @OneToMany(mappedBy = "pod")
    List<Slot> slots;
}
