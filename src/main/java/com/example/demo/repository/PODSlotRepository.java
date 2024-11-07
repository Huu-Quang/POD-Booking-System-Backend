package com.example.demo.repository;

import com.example.demo.entity.PODSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PODSlotRepository extends JpaRepository<PODSlot,Long> {
    List<PODSlot> findAllByBook_Id(Long id);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END " +
       "FROM PODSlot s " +
       "WHERE s.startTime < :end AND s.endTime > :start " +
       "AND s.book.isPaid = true")
    boolean existsByTimeRangeOverlap(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
