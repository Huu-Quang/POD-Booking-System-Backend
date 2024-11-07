package com.example.demo.model;

import lombok.Data;

import java.util.UUID;

@Data
public class BookingDetailRequest {
    long accountId;
    long slotId;

}
