package com.example.demo.service;

import com.example.demo.entity.Slot;
import com.example.demo.repository.SlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SlotService {

    @Autowired
    SlotRepository slotRepository;

    public Slot createSlot(String slotTime) {
        Slot slot = new Slot();
        slot.setSlotTime(slotTime);
        return slotRepository.save(slot);
    }

    public Slot findBySlotTime(String slotTime) {
        return slotRepository.findBySlotTime(slotTime);
    }
}
