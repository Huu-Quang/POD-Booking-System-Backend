package com.example.demo.model.Request;

import lombok.Data;

@Data
public class CoffeeShopRequest {
    private String name;
    private String address;
    private String phone;
    private String image;
    private String openTime;
    private String closeTime;
    private Long accountId; // Add this field
}
