package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import net.bytebuddy.dynamic.TypeResolutionStrategy.Lazy;

import java.time.LocalDateTime;

@Entity
public class PODSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime startTime;
    @Column(nullable = false)
    private LocalDateTime endTime;

    // Many slots can belong to one Book
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "book_id")
    private PODBooking book;
    @Column(nullable = false)
    private float price;
    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public PODBooking getBook() {
        return book;
    }

    public void setBook(PODBooking book) {
        this.book = book;
    }

    public PODSlot(Long id, LocalDateTime startTime, LocalDateTime endTime, PODBooking book) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.book = book;
    }
    public PODSlot() {
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
