package com.programacion4.apicompeja.mapper;

import com.programacion4.apicompeja.dto.MaidRequestDTO;
import com.programacion4.apicompeja.dto.MaidResponseDTO;
import com.programacion4.apicompeja.entity.Maid;
import com.programacion4.apicompeja.entity.Skill;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MaidMapper {

    public MaidResponseDTO toDTO(Maid maid) {
        return MaidResponseDTO.builder()
                .id(maid.getId())
                .alias(maid.getAlias())
                .age(maid.getAge())
                .skills(maid.getSkills().stream()
                        .map(Skill::getName)
                        .collect(Collectors.toSet()))
                .build();
    }

    public Maid toEntity(MaidRequestDTO dto) {
        return Maid.builder()
                .realName(dto.getRealName())
                .alias(dto.getAlias())
                .age(dto.getAge())
                // Las skills se setean en el Service porque necesitamos buscarlas en la BD
                .build();
    }
}