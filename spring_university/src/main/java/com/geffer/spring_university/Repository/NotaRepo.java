package com.geffer.spring_university.Repository;

import com.geffer.spring_university.Entity.Nota;
import com.geffer.spring_university.Entity.Usuario;
import com.geffer.spring_university.Entity.Materia;

import org.springframework.data.repository.CrudRepository;
import java.util.List;


public interface NotaRepo extends CrudRepository<Nota, Long> {
    List<Nota> findByEstudiante(Usuario estudiante);
    List<Nota> findByMateria(Materia materia);
}
