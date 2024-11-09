package com.example.demo.service;

import com.example.demo.entity.Account;
import com.example.demo.entity.CoffeeShop;
import com.example.demo.model.Request.CoffeeShopRequest;
import com.example.demo.repository.AccountRepository;
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
    @Autowired
    ImgurService imgurService;
    @Autowired
    AccountRepository accountRepository;

    public List<CoffeeShop> findCoffeeShopsByAddress(String address) {
        return coffeeShopRepository.findByAddress(address);
    }

    public List<CoffeeShop> findCoffeeShopsByName(String name) {
        return coffeeShopRepository.findByNameContainingIgnoreCase(name);
    }

    public List<CoffeeShop> findAll() {
        return coffeeShopRepository.findAll();
    }

    public CoffeeShop createCoffeeShop(CoffeeShopRequest coffeeShopRequest) {
        Account account = accountRepository.findById(coffeeShopRequest.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid account ID"));

        CoffeeShop coffeeShop = new CoffeeShop();
        coffeeShop.setName(coffeeShopRequest.getName());
        coffeeShop.setAddress(coffeeShopRequest.getAddress());
        coffeeShop.setPhone(coffeeShopRequest.getPhone());
        coffeeShop.setImage(coffeeShopRequest.getImage());
        coffeeShop.setOpenTime(coffeeShopRequest.getOpenTime());
        coffeeShop.setCloseTime(coffeeShopRequest.getCloseTime());
        coffeeShop.setAccount(account); // Set the Account

        return coffeeShopRepository.save(coffeeShop);
    }

    public CoffeeShop updateCoffeeShop(Long id, CoffeeShop coffeeShop) throws IOException {
        CoffeeShop existingCoffeeShop = coffeeShopRepository.findById(id).orElse(null);
        if (existingCoffeeShop != null) {
            existingCoffeeShop.setName(coffeeShop.getName());
            existingCoffeeShop.setAddress(coffeeShop.getAddress());
            existingCoffeeShop.setPhone(coffeeShop.getPhone());
            existingCoffeeShop.setOpenTime(coffeeShop.getOpenTime());
            existingCoffeeShop.setCloseTime(coffeeShop.getCloseTime());
//            if (file != null && !file.isEmpty()) {
//                String url = imgurService.uploadImage(file);
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
