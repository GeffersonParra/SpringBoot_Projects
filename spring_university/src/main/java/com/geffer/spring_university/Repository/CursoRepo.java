package com.geffer.spring_university.Repository;

import com.geffer.spring_university.Entity.Curso;
import org.springframework.data.repository.CrudRepository;

public interface CursoRepo extends CrudRepository<Curso, Long> {
}
