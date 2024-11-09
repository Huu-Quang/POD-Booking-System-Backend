package com.example.demo.controller;

import com.example.demo.entity.CoffeeShop;
import com.example.demo.entity.Product;
import com.example.demo.model.Request.CoffeeShopRequest;
import com.example.demo.service.CoffeeShopService;
//import com.example.demo.service.FirebaseStorageService;
//import com.example.demo.service.FirebaseTokenService;
import com.example.demo.service.ImgurService;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import io.jsonwebtoken.io.IOException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
    public ResponseEntity<CoffeeShop> createCoffeeShop(@RequestBody CoffeeShopRequest coffeeShopRequest) {
        CoffeeShop createdCoffeeShop = coffeeShopService.createCoffeeShop(coffeeShopRequest);
        return ResponseEntity.ok(createdCoffeeShop);
    }

    //    @PutMapping("/{id}")
//
//    public ResponseEntity<CoffeeShop> updateCoffeeShop(@PathVariable Long id, @RequestBody CoffeeShop coffeeShop) {
//        CoffeeShop updatedCoffeeShop = coffeeShopService.updateCoffeeShop(id, coffeeShop);
//        return ResponseEntity.ok(updatedCoffeeShop);
//    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CoffeeShop> updateCoffeeShop(@PathVariable Long id, @RequestBody CoffeeShop coffeeShop) throws Exception {
        CoffeeShop updatedCoffeeShop = coffeeShopService.updateCoffeeShop(id, coffeeShop);
        return ResponseEntity.ok(updatedCoffeeShop);
    }
    @Autowired
    private ImgurService imgurService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile image) {
        try {
            // Convert MultipartFile to File
            File file = convertMultiPartToFile(image);
            String response = imgurService.uploadImage(file);
            file.delete(); // Delete the temporary file
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Error occurred while uploading", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException, java.io.IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    @DeleteMapping("/{id}")

    public ResponseEntity<CoffeeShop> deleteCoffeeShop(@PathVariable Long Id) {
        CoffeeShop deleteCoffeeShop = coffeeShopService.deleteCoffeeShop(Id);
        return ResponseEntity.ok(deleteCoffeeShop);
    }
}
