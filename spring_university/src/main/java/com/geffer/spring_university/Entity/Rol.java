package com.geffer.spring_university.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "roles")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre_rol;
    private String descripcion_rol;

    @OneToMany(mappedBy = "rol")
    private Set<Usuario> usuarios;
}
