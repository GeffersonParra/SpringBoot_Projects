package com.geffer.spring_university.Repository;

import com.geffer.spring_university.Entity.Curso;
import com.geffer.spring_university.Entity.Materia;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface MateriaRepo extends CrudRepository<Materia, Long> {
    List<Materia> findBycurso(Curso curso);
}
