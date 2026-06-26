package com.programacion4.apicompeja.repository;

import com.programacion4.apicompeja.entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ShiftRepository extends JpaRepository<Shift, Long> {
    @Query("SELECT s FROM Shift s WHERE s.maid.id = :maidId AND s.shiftDate = :date")
    List<Shift> findShiftsByMaidAndDate(@Param("maidId") Long maidId, @Param("date") LocalDate date);
}
