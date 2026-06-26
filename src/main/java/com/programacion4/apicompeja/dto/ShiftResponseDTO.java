package com.programacion4.apicompeja.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class ShiftResponseDTO {
    private Long id;
    private LocalDate shiftDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String maidAlias; // Mostramos el alias en lugar de toda la entidad
    private String zoneName;  // Mostramos el nombre de la zona
}