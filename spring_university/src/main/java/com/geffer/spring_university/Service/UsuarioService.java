package com.geffer.spring_university.Service;

import com.geffer.spring_university.Entity.Curso;
import com.geffer.spring_university.Entity.Nota;
import com.geffer.spring_university.Entity.Rol;
import com.geffer.spring_university.Entity.Usuario;
import com.geffer.spring_university.Repository.UsuarioRepo;
import com.geffer.spring_university.Repository.CursoRepo;
import com.geffer.spring_university.Repository.NotaRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepo usuarioRepository;

    @Autowired
    private CursoRepo cursoRepository;

    @Autowired
    private NotaRepo notaRepository;

    public List<Usuario> obtenerTodosLosUsuarios(){
        return (List<Usuario>) usuarioRepository.findAll();
    }

    public Usuario obtenerUsuariioPorId(Long id){
        return usuarioRepository.findById(id).get();
    }

    public Usuario guardarUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public void eliminarUsuario(Long id){
        usuarioRepository.deleteById(id);
    }

    public List<Usuario> obtenerTipoUsuario(Rol rol){
        return (List<Usuario>) usuarioRepository.findByRol(rol);
    }

    public void asignarCursoAlUsuario(Long usuarioId, Long cursoId){
        Usuario usuario = usuarioRepository.findById(usuarioId).get();
        Curso curso = cursoRepository.findById(cursoId).get();

        usuario.getCursos().add(curso);
        usuarioRepository.save(usuario);
    }

    public void eliminarUsuarioDeCurso(Long usuarioId, Long cursoId){
        Usuario usuario = usuarioRepository.findById(usuarioId).get();
        Curso curso = cursoRepository.findById(cursoId).get();
        List<Nota> notas = notaRepository.findByEstudiante(usuario);

        usuario.getNotas().removeAll(notas);
        usuario.getCursos().remove(curso);
        curso.getUsuarios().remove(usuario);
        usuarioRepository.save(usuario);
        cursoRepository.save(curso);
    }

    public List<Curso> obtenerCursosDeUnUsuario(Long id){
        Usuario usuario = usuarioRepository.findById(id).get();
        return usuario.getCursos();
    }
}
