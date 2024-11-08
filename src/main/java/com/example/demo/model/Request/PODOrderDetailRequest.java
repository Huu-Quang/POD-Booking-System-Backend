package com.example.demo.model.Request;

import lombok.Data;

@Data
public class PODOrderDetailRequest {
  Long podId;
  int quantity;
}
