// src/main/java/com/example/demo/config/DataInitializer.java
package com.example.demo.config;

import com.example.demo.entity.Slot;
import com.example.demo.repository.SlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private SlotRepository slotRepository;

    @Override
    public void run(String... args) throws Exception {
        List<String> slotTimes = Arrays.asList(
                "08:00", "09:00", "10:00", "11:00", "12:00",
                "13:00", "14:00", "15:00", "16:00", "17:00",
                "18:00", "19:00", "20:00", "21:00"
        );

        for (String slotTime : slotTimes) {
            if (!slotRepository.existsBySlotTime(slotTime)) {
                Slot slot = new Slot();
                slot.setSlotTime(slotTime);
                slotRepository.save(slot);
            }
        }
    }
}