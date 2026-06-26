package com.programacion4.apicompeja.service;

import com.programacion4.apicompeja.dto.AuthRequestDTO;
import com.programacion4.apicompeja.dto.AuthResponseDTO;
import com.programacion4.apicompeja.dto.RegisterRequestDTO;
import com.programacion4.apicompeja.entity.AppUser;
import com.programacion4.apicompeja.mapper.AppUserMapper;
import com.programacion4.apicompeja.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AppUserRepository userRepository;
    private final AppUserMapper userMapper;
    private final PasswordEncoder passwordEncoder; 
    // Nota: El token real se generará mediante un JwtProvider que crearemos en la capa de seguridad.
    // De momento, dejamos la estructura lista devolviendo un token simulado o integrado si lo prefieres.

    @Transactional
    public void register(RegisterRequestDTO request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre de usuario ya está registrado");
        }

        // Encriptamos el password usando el encoder inyectado
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        AppUser user = userMapper.toEntity(request, encodedPassword);
        
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public AuthResponseDTO login(AuthRequestDTO request) {
        AppUser user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
        }

        // Aquí se conectará con el generador de JWT. Dejamos el esqueleto listo:
        String generatedToken = "MOCK_JWT_TOKEN_FOR_" + user.getUsername().toUpperCase();

        return AuthResponseDTO.builder()
                .token(generatedToken)
                .username(user.getUsername())
                .build();
    }
}