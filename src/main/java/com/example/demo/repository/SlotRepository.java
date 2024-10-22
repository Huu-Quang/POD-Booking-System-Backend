// src/main/java/com/example/demo/repository/SlotRepository.java
package com.example.demo.repository;

import com.example.demo.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;



public interface SlotRepository extends JpaRepository<Slot, Long> {
    Slot findBySlotTime(String slotTime);

    boolean existsBySlotTime(String slotTime);
}
