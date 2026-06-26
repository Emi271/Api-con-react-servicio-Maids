package com.programacion4.apicompeja.mapper;

import com.programacion4.apicompeja.dto.RegisterRequestDTO;
import com.programacion4.apicompeja.entity.AppUser;
import org.springframework.stereotype.Component;

@Component
public class AppUserMapper {

    public AppUser toEntity(RegisterRequestDTO dto, String encodedPassword) {
        return AppUser.builder()
                .username(dto.getUsername())
                .password(encodedPassword) // Guardamos la contraseña ya encriptada
                .role(dto.getRole().toUpperCase())
                .build();
    }
}