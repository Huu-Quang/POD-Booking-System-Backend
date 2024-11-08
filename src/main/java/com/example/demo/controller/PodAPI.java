package com.example.demo.controller;

import com.example.demo.entity.POD;
import com.example.demo.service.PODService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


    @RestController
    @RequestMapping("/api/PODs")
    @SecurityRequirement(name = "api")
    @CrossOrigin(origins = "*")

    public class PodAPI {


        @Autowired
        PODService podService;
        @GetMapping
        public List<POD> getAllPODs() {
            return podService.getAllPODs();
        }

        @GetMapping("/{id}")
        public ResponseEntity<POD> getPODById(@PathVariable Long id) {
            POD pod = podService.getPODById(id);
            return ResponseEntity.ok(pod);
        }

        @PostMapping

        public POD createPOD(@RequestBody POD pod) {
            return podService.createPOD(pod);
        }

        @PutMapping("/{id}")

        public ResponseEntity<POD> updatePOD(@PathVariable Long id, @RequestBody POD podDetails) {
            POD updatedPOD = podService.updatePOD(id, podDetails);
            return ResponseEntity.ok(updatedPOD);
        }

        @DeleteMapping("/{id}")

        public ResponseEntity<Void> deletePOD(@PathVariable Long id) {
            podService.deletePOD(id);
            return ResponseEntity.noContent().build();
        }
    }

