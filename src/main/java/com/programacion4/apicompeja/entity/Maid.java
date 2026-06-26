package com.programacion4.apicompeja.entity;


import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "maids")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Maid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String realName;

    @Column(nullable = false, unique = true)
    private String alias;

    private Integer age;

    // Relación ManyToMany con Skills
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "maid_skills",
        joinColumns = @JoinColumn(name = "maid_id"),
        inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> skills = new HashSet<>();

    // Relación OneToMany con Turnos
    @OneToMany(mappedBy = "maid", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Shift> shifts = new ArrayList<>();
}