package com.programacion4.apicompeja.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SkillDTO {
    private Long id;

    @NotBlank(message = "El nombre de la habilidad no puede estar vacío")
    private String name;
}