package com.example.demo.controller;

import com.example.demo.entity.POD;
//import com.example.demo.service.FirebaseStorageService;
import com.example.demo.model.Request.PODRequest;
import com.example.demo.service.ImgurService;
import com.example.demo.service.PODService;
import io.jsonwebtoken.io.IOException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.UUID;


    @RestController
    @RequestMapping("/api/PODs")
    @SecurityRequirement(name = "api")
    @CrossOrigin(origins = "*")

    public class PodAPI {

//        @Autowired
//        private FirebaseStorageService firebaseStorageService;

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
        public POD createPOD(@RequestBody PODRequest podRequest) {
            return podService.createPOD(podRequest);
        }

        @PutMapping("/{id}")
        public ResponseEntity<POD> updatePOD(@PathVariable Long id, @RequestBody PODRequest podRequest) {
            POD updatedPOD = podService.updatePOD(id, podRequest);
            return ResponseEntity.ok(updatedPOD);
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

        public ResponseEntity<Void> deletePOD(@PathVariable Long id) {
            podService.deletePOD(id);
            return ResponseEntity.noContent().build();
        }

        @GetMapping("/searchByShopId")
        public ResponseEntity<List<POD>> searchPODsByShopId(@RequestParam Long shopId) {
            List<POD> pods = podService.findPODsByShopId(shopId);
            return ResponseEntity.ok(pods);
        }

    }

