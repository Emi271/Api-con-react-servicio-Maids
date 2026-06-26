package com.programacion4.apicompeja.mapper;

import com.programacion4.apicompeja.dto.ZoneDTO;
import com.programacion4.apicompeja.entity.Zone;
import org.springframework.stereotype.Component;

@Component
public class ZoneMapper {

    public ZoneDTO toDTO(Zone zone) {
        return ZoneDTO.builder()
                .id(zone.getId())
                .name(zone.getName())
                .build();
    }

    public Zone toEntity(ZoneDTO dto) {
        return Zone.builder()
                .name(dto.getName())
                .build();
    }
}