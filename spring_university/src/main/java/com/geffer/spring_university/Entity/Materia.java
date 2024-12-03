package com.geffer.spring_university.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalTime;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@NoArgsConstructor
@Table(name = "materias")
public class Materia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre_materia;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String salon;
    private String ruta_foto;
    @ManyToOne
    @JoinColumn(name = "curso")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Curso curso;
    @ManyToOne
    @JoinColumn(name = "id_profesor")  
    private Usuario id_profesor;
    @OneToMany(mappedBy = "materia", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Nota> notas;
}
