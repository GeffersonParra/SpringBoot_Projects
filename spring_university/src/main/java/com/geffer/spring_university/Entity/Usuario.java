package com.geffer.spring_university.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "usuarios")
public class Usuario {
    @Id
    private Long id;
    private String primer_nombre;
    private String primer_apellido;
    private String ruta_foto;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuario_cursos",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "curso_id")
    )
    private List<Curso> cursos;

    @ManyToOne
    @JoinColumn(name = "rol")
    private Rol rol;

    @OneToMany(mappedBy = "id_profesor")
    private Set<Materia> materias;
    
    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Nota> notas;
}
