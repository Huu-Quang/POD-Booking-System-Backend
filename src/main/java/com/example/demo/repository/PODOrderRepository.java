package com.example.demo.repository;

import com.example.demo.entity.Account;
import com.example.demo.entity.PODOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PODOrderRepository extends JpaRepository<PODOrder, UUID> {
    List<PODOrder> findPODOrderByCustomer(Account customer);
    PODOrder findPODOrderById(UUID id);
}
