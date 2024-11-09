package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class PODBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Mapping uid to Account entity
    @ManyToOne
    @JsonIgnore
    private Account account;

    @OneToMany(mappedBy = "book",  cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<PODSlot> slots;

    @ManyToMany(mappedBy = "bookings")
    @JsonIgnore
    private List<POD> pods;

    private float totalPrice;

    private boolean isPaid;
    // Getters and Setters

    public Long getId() {
        return id;
    }
    public List<POD> getPods() {
        return pods;
    }

    public void setPods(List<POD> pods) {
        this.pods = pods;
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