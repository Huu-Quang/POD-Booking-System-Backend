package com.example.demo.service;

import com.example.demo.entity.CoffeeShop;
import com.example.demo.entity.User;
import com.example.demo.repository.CoffeeShopRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoffeeShopService {

   @Autowired
   CoffeeShopRepository coffeeShopRepository;

    public List<CoffeeShop> findCoffeeShopsByAddress(String address) {
        return coffeeShopRepository.findByAddress(address);
    }

    public CoffeeShop createCoffeeShop(CoffeeShop coffeeShop) {
        return coffeeShopRepository.save(coffeeShop);
    }

    public CoffeeShop updateCoffeeShop(Long id, CoffeeShop coffeeShop) {
        CoffeeShop existingCoffeeShop = coffeeShopRepository.findById(id).orElse(null);
        if (existingCoffeeShop != null) {
            existingCoffeeShop.setName(coffeeShop.getName());
            existingCoffeeShop.setAddress(coffeeShop.getAddress());
            existingCoffeeShop.setPhone(coffeeShop.getPhone());
            existingCoffeeShop.setOpenTime(coffeeShop.getOpenTime());
            existingCoffeeShop.setCloseTime(coffeeShop.getCloseTime());
            return coffeeShopRepository.save(existingCoffeeShop);
        }
        return coffeeShopRepository.save(coffeeShop);
    }
    public CoffeeShop deleteCoffeeShop(long id) {
        CoffeeShop coffeeShop = coffeeShopRepository.findCoffeeShopById(id);
        if (coffeeShop == null)
            throw new EntityNotFoundException("CoffeeShop not found");
        coffeeShopRepository.delete(coffeeShop);
        return coffeeShop;
    }

    }
