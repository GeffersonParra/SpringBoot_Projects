package com.geffer.spring_university.Entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "notas")
public class Nota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private float nota;
    @ManyToOne
    @JoinColumn(name = "materia")
    private Materia materia;
    @ManyToOne
    @JoinColumn(name = "estudiante")
    private Usuario estudiante;
    private LocalDate fecha;
}
