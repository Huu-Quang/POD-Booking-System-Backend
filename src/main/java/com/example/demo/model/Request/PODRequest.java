package com.example.demo.model.Request;

import lombok.Data;

@Data
public class PODRequest {
    Long id;
    String image;
    String description;
    String price;
    Long shopId;
}