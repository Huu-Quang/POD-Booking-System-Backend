package com.example.demo.repository;


import com.example.demo.entity.CoffeeShop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoffeeShopRepository extends JpaRepository<CoffeeShop, Long> {
    List<CoffeeShop> findByAddress(String address);
    CoffeeShop findCoffeeShopById(Long id);
    List<CoffeeShop> findByNameContainingIgnoreCase(String name);
}
