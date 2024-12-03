package com.geffer.spring_university.Service;

import com.geffer.spring_university.Entity.Curso;
import com.geffer.spring_university.Entity.Materia;
import com.geffer.spring_university.Entity.Usuario;
import com.geffer.spring_university.Entity.Nota;
import com.geffer.spring_university.Repository.CursoRepo;
import com.geffer.spring_university.Repository.MateriaRepo;
import com.geffer.spring_university.Repository.NotaRepo;
import com.geffer.spring_university.Repository.UsuarioRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {
    @Autowired
    private CursoRepo cursoRepository;

    @Autowired
    private UsuarioRepo usuarioRepository;

    @Autowired
    private MateriaRepo materiaRepository;

    @Autowired
    private NotaRepo notaRepository;

    public List<Curso> obtenerTodosLosCursos() {
        return (List<Curso>) cursoRepository.findAll();
    }

    public Curso obtenerCursoPorId(Long id) {
        return cursoRepository.findById(id).get();
    }

    public Curso guardarCurso(Curso curso) {
        return cursoRepository.save(curso);
    }

    public void eliminarCurso(Long id) {
        Curso curso = cursoRepository.findById(id).get();
        for (Usuario usuario : curso.getUsuarios()) {
            usuario.getCursos().remove(curso);
            usuarioRepository.save(usuario);
        }
        List<Materia> materias = materiaRepository.findBycurso(curso);
        for (Materia materia : materias) {
            List<Nota> notas = materia.getNotas();
            for (Nota notaABorrar : notas) {
                notaRepository.delete(notaABorrar);
            }
            materiaRepository.delete(materia);
        }
        cursoRepository.deleteById(id);
    }
}
