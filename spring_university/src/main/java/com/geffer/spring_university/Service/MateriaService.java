package com.geffer.spring_university.Service;

import com.geffer.spring_university.Entity.Curso;
import com.geffer.spring_university.Entity.Materia;
import com.geffer.spring_university.Repository.MateriaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MateriaService {
    @Autowired
    private MateriaRepo materiaRepository;

    public List<Materia> obtenerTodasLasMaterias(){
        return (List<Materia>) materiaRepository.findAll();
    }

    public Materia obtenerMateriaPorId(Long id){
        return materiaRepository.findById(id).get();
    }

    public Materia guardarMateria(Materia materia){
        return materiaRepository.save(materia);
    }

    public void eliminarMateria(Long id){
        materiaRepository.deleteById(id);
    }

    public List<Materia> obtenerLasMateriasDeUnCurso(Curso curso){
        return (List<Materia>) materiaRepository.findBycurso(curso);
    }
}
