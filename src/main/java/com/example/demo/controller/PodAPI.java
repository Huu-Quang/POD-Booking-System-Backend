package com.example.demo.controller;

import com.example.demo.entity.POD;
import com.example.demo.service.FirebaseStorageService;
import com.example.demo.service.PODService;
import com.example.demo.util.ImageToAzureUtil;
import com.microsoft.azure.storage.StorageException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;


    @RestController
    @RequestMapping("/api/PODs")
    @SecurityRequirement(name = "api")
    @CrossOrigin(origins = "*")
    public class PodAPI {

        @Autowired
        private ImageToAzureUtil imageToAzureUtil;

        @Autowired
        PODService podService;
        @GetMapping
        public List<POD> getAllPODs() {
            return podService.getAllPODs();
        }

        @GetMapping("/{id}")
        public ResponseEntity<POD> getPODById(@PathVariable UUID id) {
            POD pod = podService.getPODById(id);
            return ResponseEntity.ok(pod);
        }

        @PostMapping
        public POD createPOD(@RequestBody POD pod) {
            return podService.createPOD(pod);
        }

        @PutMapping("/{id}")
        public ResponseEntity<POD> updatePOD(@PathVariable UUID id, @RequestPart("file") MultipartFile file, @RequestBody POD podDetails) {
            POD updatedPOD = podService.updatePOD(id, file, podDetails);
            return ResponseEntity.ok(updatedPOD);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deletePOD(@PathVariable UUID id) {
            podService.deletePOD(id);
            return ResponseEntity.noContent().build();
        }
//
//        @PostMapping("/upload-image")
//        public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) throws IOException, URISyntaxException, StorageException {
//            return ResponseEntity.ok(imageToAzureUtil.uploadImage(file));
//        }
@Autowired
private FirebaseStorageService firebaseStorageService;

        @PostMapping("/upload")
        public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
            try {
                String imageUrl = firebaseStorageService.uploadFile(file);
                return ResponseEntity.ok(imageUrl);
            } catch (Exception e) {
                return ResponseEntity.status(500).body("Failed to upload image: " + e.getMessage());
            }
        }
    }

