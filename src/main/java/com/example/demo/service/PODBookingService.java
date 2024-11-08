package com.example.demo.service;

import com.example.demo.config.SecurityConfig;
import com.example.demo.entity.PODBooking;
import com.example.demo.entity.PODSlot;
import com.example.demo.model.PODBookingResponse;
import com.example.demo.repository.PODBookingRepository;
import com.example.demo.repository.PODSlotRepository;

import org.aspectj.weaver.patterns.ConcreteCflowPointcut.Slot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springfox.documentation.spi.service.contexts.SecurityContext;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PODBookingService {
    @Autowired
    PODBookingRepository podBookingRepository;
    @Autowired
    PODSlotRepository podSlotRepository;
    @Autowired
    AuthenticationService authenticationService;

    public PODBooking createBooking(LocalDateTime start, LocalDateTime end) {
        timeConstraints(start,end);
        PODSlot slot = new PODSlot();
        slot.setStartTime(start);
        slot.setEndTime(end);
        slot.setPrice((end.getHour() - start.getHour()) * 100000);

        PODBooking booking;

        booking = new PODBooking();
        booking.setAccount(authenticationService.getCurrentAccount());

        slot.setBook(booking);

        booking.setSlots(List.of(slot));

        return this.podBookingRepository.save(booking);


    }
    public PODSlot createNewSlot(LocalDateTime start, LocalDateTime end, Long bookId) {
        timeConstraints(start,end);
        PODBooking booking = this.podBookingRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Booking not found"));
        if(booking.isIsPaid()){
            return null;
        }
        PODSlot slot = new PODSlot();
        slot.setStartTime(start);
        slot.setEndTime(end);
        slot.setPrice((float) ((Duration.between(start, end).toMinutes())/60.0 * 100000));
        slot.setBook(booking);

        return this.podSlotRepository.save(slot);
    }
    private void timeConstraints(LocalDateTime start, LocalDateTime end){
        if (start.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("The start time must from now.");
        }
        if(!end.toLocalDate().equals(start.toLocalDate())){
            throw new IllegalArgumentException("The end time must be in the same day as start time.");
        }
        boolean slotOverlaps = podSlotRepository.existsByTimeRangeOverlap(start, end);
        if (slotOverlaps) {
            throw new IllegalArgumentException("A slot with an overlapping time range already exists.");
        }
    }

    public PODBookingResponse checkOut(Long id){
        PODBookingResponse response = new PODBookingResponse();
        PODBooking booking = this.podBookingRepository.findById(id).orElseThrow();
        response.setBookID(id);
        List<PODSlot> eSlots = booking.getSlots();
        for (PODSlot podSlot : eSlots) {
           if(podSlotRepository.existsByTimeRangeOverlap(podSlot.getStartTime(),podSlot.getEndTime())){
                eSlots.remove(podSlot);
           } 
        }
        List<PODSlot> slots = eSlots.stream()
        .map(slot -> {PODSlot detached = new PODSlot(slot.getId(),slot.getStartTime(),slot.getEndTime(),null);
            return detached;
        })
        .collect(Collectors.toList());

        response.setSlots(slots);
        float totalPrice = (float) booking.getSlots().stream().mapToDouble(PODSlot::getPrice).sum();
        response.setTotalPrice(totalPrice);
        booking.setTotalPrice(totalPrice);
        return response;
    }

    public PODBookingResponse pay(Long id){
        PODBookingResponse response = new PODBookingResponse();
        PODBooking booking = this.podBookingRepository.findById(id).orElseThrow();
    
        response.setBookID(id);
        List<PODSlot> eSlots = booking.getSlots();
        List<PODSlot> slots = eSlots.stream()
        .map(slot -> {PODSlot detached = new PODSlot(slot.getId(),slot.getStartTime(),slot.getEndTime(),null);
            return detached;
        })
        .collect(Collectors.toList());

        response.setSlots(slots);
        float totalPrice = (float) booking.getSlots().stream().mapToDouble(PODSlot::getPrice).sum();
        response.setTotalPrice(totalPrice);
        if(booking.isIsPaid()){
            return response;
        }
        booking.setTotalPrice(totalPrice);
        booking.setIsPaid(true);
        return response;
    }
}
