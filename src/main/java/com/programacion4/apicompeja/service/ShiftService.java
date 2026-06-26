package com.programacion4.apicompeja.service;

import com.programacion4.apicompeja.dto.ShiftRequestDTO;
import com.programacion4.apicompeja.dto.ShiftResponseDTO;
import com.programacion4.apicompeja.entity.Maid;
import com.programacion4.apicompeja.entity.Shift;
import com.programacion4.apicompeja.entity.Zone;
import com.programacion4.apicompeja.mapper.ShiftMapper;
import com.programacion4.apicompeja.repository.MaidRepository;
import com.programacion4.apicompeja.repository.ShiftRepository;
import com.programacion4.apicompeja.repository.ZoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShiftService {

    private final ShiftRepository shiftRepository;
    private final MaidRepository maidRepository;
    private final ZoneRepository zoneRepository;
    private final ShiftMapper shiftMapper;

    @Transactional
    public ShiftResponseDTO createShift(ShiftRequestDTO request) {
        // 1. Validar que la Maid exista
        Maid maid = maidRepository.findById(request.getMaidId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Maid no encontrada"));

        // 2. Validar que la Zona exista
        Zone zone = zoneRepository.findById(request.getZoneId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Zona no encontrada"));

        // 3. Validar consistencia del horario
        if (request.getStartTime().isAfter(request.getEndTime()) || request.getStartTime().equals(request.getEndTime())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La hora de inicio debe ser anterior a la hora de fin");
        }

        // 4. Lógica de negocio compleja: Validar solapamiento de horarios para la misma Maid
        List<Shift> existingShifts = shiftRepository.findShiftsByMaidAndDate(request.getMaidId(), request.getShiftDate());
        
        for (Shift existingShift : existingShifts) {
            boolean overlaps = request.getStartTime().isBefore(existingShift.getEndTime()) 
                            && request.getEndTime().isAfter(existingShift.getStartTime());
            
            if (overlaps) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                    "Conflict de horarios: La Maid '" + maid.getAlias() + "' ya tiene un turno asignado en ese horario (" 
                    + existingShift.getStartTime() + " - " + existingShift.getEndTime() + ")");
            }
        }

        // 5. Mapear y guardar
        Shift shift = shiftMapper.toEntity(request);
        shift.setMaid(maid);
        shift.setZone(zone);

        Shift savedShift = shiftRepository.save(shift);
        return shiftMapper.toDTO(savedShift);
    }

    @Transactional(readOnly = true)
    public List<ShiftResponseDTO> getShiftsByMaidAndDate(Long maidId, LocalDate date) {
        if (!maidRepository.existsById(maidId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Maid no encontrada");
        }
        
        return shiftRepository.findShiftsByMaidAndDate(maidId, date).stream()
                .map(shiftMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void cancelShift(Long id) {
        if (!shiftRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El turno no existe");
        }
        shiftRepository.deleteById(id);
    }
}
