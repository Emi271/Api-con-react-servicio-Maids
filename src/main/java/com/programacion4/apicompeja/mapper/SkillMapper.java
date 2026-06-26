package com.programacion4.apicompeja.mapper;

import com.programacion4.apicompeja.dto.SkillDTO;
import com.programacion4.apicompeja.entity.Skill;
import org.springframework.stereotype.Component;

@Component
public class SkillMapper {

    public SkillDTO toDTO(Skill skill) {
        return SkillDTO.builder()
                .id(skill.getId())
                .name(skill.getName())
                .build();
    }

    public Skill toEntity(SkillDTO dto) {
        return Skill.builder()
                .name(dto.getName())
                .build();
    }
}