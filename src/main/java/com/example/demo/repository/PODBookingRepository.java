package com.example.demo.repository;

import com.example.demo.entity.PODBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PODBookingRepository extends JpaRepository<PODBooking,Long> {
}
