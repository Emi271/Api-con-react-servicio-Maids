package com.programacion4.apicompeja.service;

import com.programacion4.apicompeja.dto.MaidRequestDTO;
import com.programacion4.apicompeja.dto.MaidResponseDTO;
import com.programacion4.apicompeja.entity.Maid;
import com.programacion4.apicompeja.entity.Skill;
import com.programacion4.apicompeja.mapper.MaidMapper;
import com.programacion4.apicompeja.repository.MaidRepository;
import com.programacion4.apicompeja.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MaidService {

    private final MaidRepository maidRepository;
    private final SkillRepository skillRepository;
    private final MaidMapper maidMapper;

    @Transactional(readOnly = true)
    public List<MaidResponseDTO> getAllMaids() {
        return maidRepository.findAll().stream()
                .map(maidMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MaidResponseDTO> getMaidsBySkill(String skillName) {
        return maidRepository.findMaidsBySkillName(skillName).stream()
                .map(maidMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MaidResponseDTO getMaidById(Long id) {
        Maid maid = maidRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Maid no encontrada"));
        return maidMapper.toDTO(maid);
    }

    @Transactional
    public MaidResponseDTO createMaid(MaidRequestDTO request) {
        if (maidRepository.findByAlias(request.getAlias()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El alias ya está en uso");
        }

        Maid maid = maidMapper.toEntity(request);
        maid.setSkills(getSkillsFromIds(request.getSkillIds())); // Asignamos las entidades Skill

        Maid savedMaid = maidRepository.save(maid);
        return maidMapper.toDTO(savedMaid);
    }

    @Transactional
    public MaidResponseDTO updateMaid(Long id, MaidRequestDTO request) {
        Maid maid = maidRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Maid no encontrada"));

        // Validar si el nuevo alias ya existe en OTRA maid
        maidRepository.findByAlias(request.getAlias()).ifPresent(existingMaid -> {
            if (!existingMaid.getId().equals(id)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El alias ya está en uso");
            }
        });

        maid.setRealName(request.getRealName());
        maid.setAlias(request.getAlias());
        maid.setAge(request.getAge());
        maid.setSkills(getSkillsFromIds(request.getSkillIds()));

        return maidMapper.toDTO(maidRepository.save(maid));
    }

    @Transactional
    public void deleteMaid(Long id) {
        if (!maidRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Maid no encontrada");
        }
        maidRepository.deleteById(id);
    }

    // Método auxiliar para buscar las habilidades en la BD a partir de los IDs que manda el DTO
    private Set<Skill> getSkillsFromIds(Set<Long> skillIds) {
        Set<Skill> skills = new HashSet<>();
        for (Long skillId : skillIds) {
            Skill skill = skillRepository.findById(skillId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Habilidad con ID " + skillId + " no existe"));
            skills.add(skill);
        }
        return skills;
    }
}