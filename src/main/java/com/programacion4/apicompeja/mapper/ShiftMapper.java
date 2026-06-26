package com.programacion4.apicompeja.mapper;

import com.programacion4.apicompeja.dto.ShiftRequestDTO;
import com.programacion4.apicompeja.dto.ShiftResponseDTO;
import com.programacion4.apicompeja.entity.Shift;
import org.springframework.stereotype.Component;

@Component
public class ShiftMapper {

    public ShiftResponseDTO toDTO(Shift shift) {
        return ShiftResponseDTO.builder()
                .id(shift.getId())
                .shiftDate(shift.getShiftDate())
                .startTime(shift.getStartTime())
                .endTime(shift.getEndTime())
                .maidAlias(shift.getMaid().getAlias())
                .zoneName(shift.getZone().getName())
                .build();
    }

    public Shift toEntity(ShiftRequestDTO dto) {
        return Shift.builder()
                .shiftDate(dto.getShiftDate())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                // Maid y Zone se setean en el Service buscando en la base de datos
                .build();
    }
}