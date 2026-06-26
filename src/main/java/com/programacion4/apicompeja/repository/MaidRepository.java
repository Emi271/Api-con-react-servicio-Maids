package com.programacion4.apicompeja.repository;

import com.programacion4.apicompeja.entity.Maid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MaidRepository extends JpaRepository<Maid, Long> {
    Optional<Maid> findByAlias(String alias);

    @Query("SELECT m FROM Maid m JOIN m.skills s WHERE s.name = :skillName")
    List<Maid> findMaidsBySkillName(@Param("skillName") String skillName);
}
