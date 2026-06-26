package com.programacion4.apicompeja.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "skills")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // Ej: "Tsundere", "Latte Art", "Moe Moe Kyun"

    @ManyToMany(mappedBy = "skills")
    private Set<Maid> maids = new HashSet<>();
}