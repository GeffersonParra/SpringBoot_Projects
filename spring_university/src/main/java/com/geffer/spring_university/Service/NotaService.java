package com.geffer.spring_university.Service;

import com.geffer.spring_university.Entity.Materia;
import com.geffer.spring_university.Entity.Nota;
import com.geffer.spring_university.Entity.Usuario;
import com.geffer.spring_university.Repository.NotaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class NotaService {
    @Autowired
    private NotaRepo notaRepository;

    public List<Nota> obtenerTodasLasNotas(){
        return (List<Nota>) notaRepository.findAll();
    }

    public Nota obtenerNotaPorId(Long id){
        return notaRepository.findById(id).get();
    }

    public Nota guardarNota(Nota nota){
        return notaRepository.save(nota);
    }

    public void eliminarNota(Long id){
        notaRepository.deleteById(id);
    }

    public List<Nota> obtenerNotasDeUnEstudiante(Usuario estudiante){
        return notaRepository.findByEstudiante(estudiante);
    }

    public List<Nota> obtenerNotasDeUnaMateria(Materia materia){
        return notaRepository.findByMateria(materia);
    }
}
