package com.programacion4.apicompeja.dto;

import lombok.Builder;
import lombok.Data;
import java.util.Set;

@Data
@Builder
public class MaidResponseDTO {
    private Long id;
    private String alias;
    private Integer age;
    private Set<String> skills; // Solo devolvemos los nombres de las habilidades
}