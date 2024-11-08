package com.example.demo.service;

import com.example.demo.entity.CoffeeShop;
import com.example.demo.repository.CoffeeShopRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CoffeeShopService {

    @Autowired
    CoffeeShopRepository coffeeShopRepository;
//    @Autowired
//    FirebaseStorageService firebaseStorageService;

    public List<CoffeeShop> findCoffeeShopsByAddress(String address) {
        return coffeeShopRepository.findByAddress(address);
    }

    public List<CoffeeShop> findCoffeeShopsByName(String name) {
        return coffeeShopRepository.findByNameContainingIgnoreCase(name);
    }

    public List<CoffeeShop> findAll() {
        return coffeeShopRepository.findAll();
    }

    public CoffeeShop createCoffeeShop(CoffeeShop coffeeShop) {
        return coffeeShopRepository.save(coffeeShop);
    }

    public CoffeeShop updateCoffeeShop(Long id, MultipartFile file, CoffeeShop coffeeShop) throws IOException {
        CoffeeShop existingCoffeeShop = coffeeShopRepository.findById(id).orElse(null);
        if (existingCoffeeShop != null) {
            existingCoffeeShop.setName(coffeeShop.getName());
            existingCoffeeShop.setAddress(coffeeShop.getAddress());
            existingCoffeeShop.setPhone(coffeeShop.getPhone());
            existingCoffeeShop.setOpenTime(coffeeShop.getOpenTime());
            existingCoffeeShop.setCloseTime(coffeeShop.getCloseTime());
//            if (file != null && !file.isEmpty()) {
//                String url = firebaseStorageService.uploadFile(file);
//                existingCoffeeShop.setImage(url);
//            }

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
