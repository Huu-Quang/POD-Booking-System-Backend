package com.example.demo.service;

import com.example.demo.entity.POD;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.repository.PODRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PODService {

    @Autowired
    private PODRepository podRepository;

    public List<POD> getAllPODs() {
        return podRepository.findAll();
    }

    public POD getPODById(Long id) {
        return podRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("POD not found"));
    }

    public POD createPOD(POD pod) {
        return podRepository.save(pod);
    }

    public POD updatePOD(Long id, POD podDetails) {
        POD pod = podRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("POD not found"));
        pod.setImage(podDetails.getImage());
        pod.setDescription(podDetails.getDescription());
        pod.setPrice(podDetails.getPrice());
        return podRepository.save(pod);
    }

    public void deletePOD(Long id) {
        POD pod = podRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("POD not found"));
        podRepository.delete(pod);
    }
}