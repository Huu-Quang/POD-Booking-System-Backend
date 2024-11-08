package com.example.demo.repository;

import com.example.demo.entity.POD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PODRepository extends JpaRepository<POD, Long> {

    POD findPODById(Long id);
}