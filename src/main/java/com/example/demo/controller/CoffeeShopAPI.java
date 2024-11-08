package com.example.demo.controller;

import com.example.demo.entity.CoffeeShop;
import com.example.demo.entity.Product;
import com.example.demo.service.CoffeeShopService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/coffeeshops")
@SecurityRequirement(name = "api")
@CrossOrigin(origins = "*")
public class CoffeeShopAPI {

    @Autowired
    CoffeeShopService coffeeShopService;

    @GetMapping("/searchByAddress")
    public ResponseEntity<List<CoffeeShop>> searchCoffeeShops(@RequestParam String address) {
        List<CoffeeShop> coffeeShops = coffeeShopService.findCoffeeShopsByAddress(address);
        return ResponseEntity.ok(coffeeShops);
    }
    @GetMapping("/searchByName")
    public ResponseEntity<List<CoffeeShop>> searchCoffeeShopsByName(@RequestParam String name) {
        List<CoffeeShop> coffeeShops = coffeeShopService.findCoffeeShopsByName(name);
        return ResponseEntity.ok(coffeeShops);
    }
    @GetMapping
    public ResponseEntity<List<CoffeeShop>> getAllCoffeeShops() {
        List<CoffeeShop> coffeeShops = coffeeShopService.findAll();
        return ResponseEntity.ok(coffeeShops);
    }


    @PostMapping("/create")

    public ResponseEntity<CoffeeShop> createCoffeeShop(@RequestBody CoffeeShop coffeeShop) {
        CoffeeShop createdCoffeeShop = coffeeShopService.createCoffeeShop(coffeeShop);
        return ResponseEntity.ok(createdCoffeeShop);
    }

    @PutMapping("/{id}")

    public ResponseEntity<CoffeeShop> updateCoffeeShop(@PathVariable Long id, @RequestBody CoffeeShop coffeeShop) {
        CoffeeShop updatedCoffeeShop = coffeeShopService.updateCoffeeShop(id, coffeeShop);
        return ResponseEntity.ok(updatedCoffeeShop);
    }

    @DeleteMapping("/{id}")

    public ResponseEntity<CoffeeShop> deleteCoffeeShop(@PathVariable Long Id) {
        CoffeeShop deleteCoffeeShop = coffeeShopService.deleteCoffeeShop(Id);
        return ResponseEntity.ok(deleteCoffeeShop);
    }
}
