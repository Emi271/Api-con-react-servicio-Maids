package com.programacion4.apicompeja.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.Set;

@Data
public class MaidRequestDTO {
    @NotBlank(message = "El nombre real es obligatorio")
    private String realName;

    @NotBlank(message = "El alias es obligatorio")
    private String alias;

    @Min(value = 18, message = "Debe ser mayor de edad")
    private Integer age;

    @NotEmpty(message = "Debe tener al menos una habilidad (IDs)")
    private Set<Long> skillIds;
}