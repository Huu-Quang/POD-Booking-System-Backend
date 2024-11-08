package com.example.demo.model;

import com.example.demo.entity.PODOrderDetail;
import lombok.Data;

import java.util.List;

@Data
public class PODOrderRequest {
    List<PODOrderDetailRequest> detail;
}
