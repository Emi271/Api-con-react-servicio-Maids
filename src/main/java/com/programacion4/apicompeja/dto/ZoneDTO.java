package com.programacion4.apicompeja.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ZoneDTO {
    private Long id;
    
    @NotBlank(message = "El nombre de la zona no puede estar vacío")
    private String name;
}