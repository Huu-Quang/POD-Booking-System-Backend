package com.example.demo.service;

import com.example.demo.entity.Account;
import com.example.demo.entity.OrderDetail;
import com.example.demo.entity.Orders;
import com.example.demo.entity.Product;
import com.example.demo.model.OrderDetailRequest;
import com.example.demo.model.OrderRequest;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    ProductRepository productRepository;

    public Orders create(OrderRequest orderRequest) {

        Account customer = authenticationService.getCurrentAccount();

        Orders order = new Orders();
        List<OrderDetail> orderDetails = new ArrayList<>();
        float total = 0;

        order.setCustomer(customer);
        order.setDate(new Date());

        for (OrderDetailRequest orderDetailRequest : orderRequest.getDetail()) {

            Product product = productRepository.findProductById(orderDetailRequest.getProductId());

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setQuantity(orderDetailRequest.getQuantity());
            orderDetail.setPrice(Float.parseFloat(product.getPrice()));
            orderDetail.setProduct(product);
            orderDetail.setOrder(order);


            orderDetails.add(orderDetail);

            total += Float.parseFloat(product.getPrice()) * orderDetailRequest.getQuantity();

        }
        order.setOrderDetail(orderDetails);
        order.setTotalPrice(total);

        return orderRepository.save(order);

    }
}