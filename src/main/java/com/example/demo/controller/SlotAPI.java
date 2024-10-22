package com.example.demo.controller;

import com.example.demo.entity.Slot;
import com.example.demo.service.SlotService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/slot")
@SecurityRequirement(name = "api")
@CrossOrigin(origins = "*")
public class SlotAPI {


    @Autowired
    SlotService slotService;
    @PostMapping("/create")
    public ResponseEntity<Slot> createSlot(@RequestParam String slotTime) {
        Slot slot = slotService.createSlot(slotTime);
        return ResponseEntity.ok(slot);
    }

}

