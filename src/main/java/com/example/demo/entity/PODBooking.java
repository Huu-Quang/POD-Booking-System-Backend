package com.example.demo.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class PODBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Mapping uid to Account entity
    @ManyToOne
    private Account account;

    @OneToMany(mappedBy = "book",  cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<PODSlot> slots;

    private float totalPrice;

    private boolean isPaid;
    // Getters and Setters

    public Long getId() {
        return id;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public boolean isIsPaid() {
        return isPaid;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<PODSlot> getSlots() {
        return slots;
    }

    public void setSlots(List<PODSlot> slots) {
        this.slots = slots;
    }
}