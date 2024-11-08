package com.example.demo.service;

import com.example.demo.entity.PODBooking;
import com.example.demo.entity.PODSlot;
import com.example.demo.repository.PODBookingRepository;
import com.example.demo.repository.PODSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
public class PODBookingService {
    @Autowired
    PODBookingRepository podBookingRepository;
    @Autowired
    PODSlotRepository podSlotRepository;
    @Autowired
    AuthenticationService authenticationService;

    public String createBooking(LocalDateTime start, LocalDateTime end) {
        LocalDateTime now = LocalDateTime.now();

        // Check if start time is equal to end time
        if (start.isEqual(end)) {
            return "Bạn không được book trùng giờ";
        }

        // Check if start time is after end time
        if (start.isAfter(end)) {
            return "Startime phải nhỏ hơn Endtime";
        }

        // Check if the booking time is within the allowed range
        if (start.toLocalTime().isBefore(LocalTime.of(7, 0)) || end.toLocalTime().isAfter(LocalTime.of(22, 0))) {
            return "Quán đã đóng cửa";
        }

        // Check if the booking date is valid (from tomorrow and within one week)
        if (start.toLocalDate().isBefore(now.toLocalDate().plusDays(1)) || start.toLocalDate().isAfter(now.toLocalDate().plusWeeks(1))) {
            return "Ngày book không hợp lệ";
        }

        // Check if the slot already exists
        boolean slotOverlaps = podSlotRepository.existsByTimeRangeOverlap(start, end);
        if (slotOverlaps) {
            return "Slot đã tồn tại";
        }

        // Create and save the booking
        PODSlot slot = new PODSlot();
        slot.setStartTime(start);
        slot.setEndTime(end);
        slot.setPrice((end.getHour() - start.getHour()) * 100000);

        PODBooking booking = new PODBooking();
        booking.setAccount(authenticationService.getCurrentAccount());
        slot.setBook(booking);
        booking.setSlots(List.of(slot));

        float totalPrice = booking.getSlots().stream().map(PODSlot::getPrice).reduce(0.0f, Float::sum);

        booking.setTotalPrice(totalPrice);

        podBookingRepository.save(booking);

        return "Booking created successfully";
    }


    public List<PODBooking> getAllBookingsByAccount(Long accountId) {
        return podBookingRepository.findByAccountId(accountId);
    }

    public float calculateTotalPrice(Long accountId) {
        List<PODBooking> bookings = podBookingRepository.findByAccountId(accountId);
        return bookings.stream().map(PODBooking::getTotalPrice).reduce(0.0f, Float::sum);
    }

    public float calculateTotalAmountSpent(Long accountId) {
        List<PODBooking> bookings = podBookingRepository.findByAccountId(accountId);
        return bookings.stream()
                .flatMap(booking -> booking.getSlots().stream())
                .map(PODSlot::getPrice)
                .reduce(0.0f, Float::sum);
    }

    public String updateBooking(Long id, LocalDateTime start, LocalDateTime end) {
        PODBooking booking = podBookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));
        // Update booking details
        booking.getSlots().forEach(slot -> {
            slot.setStartTime(start);
            slot.setEndTime(end);
            slot.setPrice((end.getHour() - start.getHour()) * 100000);
        });
        podBookingRepository.save(booking);
        return "Booking updated successfully";
    }

    public void deleteBooking(Long id) {
        PODBooking booking = podBookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));
        podSlotRepository.deleteAll(booking.getSlots());
        podBookingRepository.deleteById(id);
    }

    public float getBookingId(Long bookingId) {
        PODBooking booking = podBookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        return booking.getTotalPrice();
    }
}
