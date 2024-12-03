package com.geffer.spring_university.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.geffer.spring_university.Entity.Rol;
import com.geffer.spring_university.Entity.Usuario;
import com.geffer.spring_university.Entity.Curso;
import com.geffer.spring_university.Service.CursoService;
import com.geffer.spring_university.Service.RolService;
import com.geffer.spring_university.Service.UsuarioService;

import jakarta.transaction.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;

    @Autowired
    private CursoService cursoService;

    private static final String UPLOAD_DIR = ("src/main/resources/static/uploads/usuarios/").toString();
    private Path uploadDir = Paths.get(UPLOAD_DIR);

    @GetMapping("/estudiantes")
    public String mostrarEstudiantes(Model model) {
        Rol rol = rolService.obtenerRolPorId((long) 2);
        List<Usuario> estudiantes = usuarioService.obtenerTipoUsuario(rol);
        model.addAttribute("estudiantes", estudiantes);
        return "usuarios/MostrarEstudiantes";
    }

    @GetMapping("/estudiantes/new")
    public String nuevoEstudiante() {
        return "usuarios/CrearEstudiante";
    }

    @PostMapping("/estudiantes/create")
    public String guardarEstudiante(@RequestParam MultipartFile foto, @ModelAttribute Usuario usuario, Model model)
            throws IOException {
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        String uniqueFileName = UUID.randomUUID().toString() + foto.getOriginalFilename().toString();
        Rol rol = rolService.obtenerRolPorId((long) 2);
        Path destinationFile = uploadDir.resolve(uniqueFileName);
        foto.transferTo(destinationFile);
        usuario.setRuta_foto("/uploads/usuarios/" + uniqueFileName.toString());
        usuario.setRol(rol);
        usuarioService.guardarUsuario(usuario);
        List<Usuario> estudiantes = usuarioService.obtenerTipoUsuario(rol);
        model.addAttribute("estudiantes", estudiantes);
        return "redirect:/estudiantes";
    }

    @GetMapping("/estudiantes/view/{id}")
    public String verEstudiante(@PathVariable("id") Long id, Model model) {
        Usuario estudiante = usuarioService.obtenerUsuariioPorId(id);
        model.addAttribute("estudiante", estudiante);
        List<Curso> cursos = usuarioService.obtenerCursosDeUnUsuario(id);
        model.addAttribute("cursos", cursos);
        return "usuarios/VerEstudiante";
    }

    @Transactional
    @GetMapping("/estudiantes/assign/{id}")
    public String asignarCurso(@PathVariable("id") Long id, Model model) {
        Usuario estudiante = usuarioService.obtenerUsuariioPorId(id);
        List<Curso> todosCursos = cursoService.obtenerTodosLosCursos();
        List<Curso> cursosUsuario = usuarioService.obtenerCursosDeUnUsuario(id);
        todosCursos.removeAll(cursosUsuario);
        model.addAttribute("cursos", todosCursos);
        model.addAttribute("estudiante", estudiante);
        return "usuarios/AsignarCurso";
    }

    @PostMapping("/estudiantes/assign/{id}")
    public String guardarAsignacionCurso(@RequestParam Long usuario_id, @RequestParam Long curso_id) {
        usuarioService.asignarCursoAlUsuario(usuario_id, curso_id);
        Usuario estudiante = usuarioService.obtenerUsuariioPorId(usuario_id);
        return "redirect:/estudiantes/view/" + estudiante.getId();
    }

    @GetMapping("/estudiantes/edit/{id}")
    public String editarUsuario(@PathVariable("id") Long id, Model model) {
        Usuario estudiante = usuarioService.obtenerUsuariioPorId(id);
        model.addAttribute("estudiante", estudiante);
        return "usuarios/EditarEstudiante";
    }

    @PostMapping("/estudiantes/update/{id}")
    public String actualizarEstudiante(@RequestParam("foto") MultipartFile foto, @PathVariable("id") Long id,
            @ModelAttribute("usuario") Usuario usuario) throws IOException {
        Usuario usuarioExistente = usuarioService.obtenerUsuariioPorId(id);
        usuarioExistente.setPrimer_nombre(usuario.getPrimer_nombre());
        usuarioExistente.setPrimer_apellido(usuario.getPrimer_apellido());

        if (!foto.isEmpty()) {
            String fotoAnterior = usuarioExistente.getRuta_foto();
            String rutaCompleta = "src/main/resources/static".toString() + fotoAnterior;
            java.io.File archivoAnterior = new java.io.File(rutaCompleta);
            archivoAnterior.delete();
            String uniqueFileName = foto.getOriginalFilename().replace(" ", "_");
            Path destinationFile = uploadDir.resolve(uniqueFileName);
            foto.transferTo(destinationFile);
            usuarioExistente.setRuta_foto("/uploads/usuarios/" + uniqueFileName);
        }
        usuarioService.guardarUsuario(usuarioExistente);
        return "redirect:/estudiantes";
    }

    @PostMapping("/estudiantes/delete/{id}")
    public String eliminarUsuario(@PathVariable("id") Long id) {
        Usuario usuario = usuarioService.obtenerUsuariioPorId(id);
        String rutaCompleta = "src/main/resources/static".toString() + usuario.getRuta_foto().toString();
        java.io.File archivoAnterior = new java.io.File(rutaCompleta);
        usuarioService.eliminarUsuario(id);
        archivoAnterior.delete();
        return "redirect:/estudiantes";
    }

    @GetMapping("/profesores")
    public String mostrarProfesores(Model model) {
        Rol rol = rolService.obtenerRolPorId((long) 1);
        List<Usuario> profesores = usuarioService.obtenerTipoUsuario(rol);
        model.addAttribute("profesores", profesores);
        return "usuarios/MostrarProfesores";
    }

    @GetMapping("/profesores/new")
    public String nuevoProfesor() {
        return "usuarios/CrearProfesor";
    }

    @PostMapping("/profesores/create")
    public String guardarProfesor(@RequestParam MultipartFile foto, @ModelAttribute Usuario usuario, Model model)
            throws IOException {
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        String uniqueFileName = UUID.randomUUID().toString() + foto.getOriginalFilename().toString();
        Rol rol = rolService.obtenerRolPorId((long) 1);
        Path destinationFile = uploadDir.resolve(uniqueFileName);
        foto.transferTo(destinationFile);
        usuario.setRuta_foto("/uploads/usuarios/" + uniqueFileName.toString());
        usuario.setRol(rol);
        usuarioService.guardarUsuario(usuario);
        List<Usuario> profesores = usuarioService.obtenerTipoUsuario(rol);
        model.addAttribute("profesores", profesores);
        return "redirect:/profesores";
    }
}
