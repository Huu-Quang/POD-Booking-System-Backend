package com.example.demo.service;

import com.example.demo.entity.Account;
import com.example.demo.entity.CoffeeShop;
import com.example.demo.entity.POD;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.model.Request.PODRequest;
import com.example.demo.repository.CoffeeShopRepository;
import com.example.demo.repository.PODRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class PODService {

    @Autowired
    private PODRepository podRepository;

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private CoffeeShopRepository coffeeShopRepository;
    public List<POD> getAllPODs() {
        return podRepository.findAll();
    }

    public POD getPODById(Long id) {
        return podRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("POD not found"));
    }

    public POD createPOD(PODRequest podRequest) {
        Account account = authenticationService.getCurrentAccount();
        CoffeeShop coffeeShop = coffeeShopRepository.findCoffeeShopById(podRequest.getShopId());

        POD pod = new POD();
        pod.setImage(podRequest.getImage());
        pod.setDescription(podRequest.getDescription());
        pod.setPrice(podRequest.getPrice());
        pod.setAccount(account);
        pod.setCoffeeShop(coffeeShop);

        return podRepository.save(pod);
    }



    public POD updatePOD(Long id, PODRequest podRequest) {
        POD pod = podRepository.findById(podRequest.getId()).orElseThrow(() -> new EntityNotFoundException("POD not found"));
        CoffeeShop coffeeShop = coffeeShopRepository.findCoffeeShopById(podRequest.getShopId());



        pod.setImage(podRequest.getImage());
        pod.setDescription(podRequest.getDescription());
        pod.setPrice(podRequest.getPrice());
        pod.setCoffeeShop(coffeeShop);

        return podRepository.save(pod);
    }

    public void deletePOD(Long id) {
        POD pod = podRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("POD not found"));
        podRepository.delete(pod);
    }
    public List<POD> findPODsByShopId(Long shopId) {
        return podRepository.findPODsByCoffeeShop_Id(shopId);
    }
}