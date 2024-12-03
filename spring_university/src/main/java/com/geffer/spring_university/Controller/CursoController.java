package com.geffer.spring_university.Controller;

import com.geffer.spring_university.Entity.Curso;
import com.geffer.spring_university.Entity.Materia;
import com.geffer.spring_university.Entity.Usuario;
import com.geffer.spring_university.Service.CursoService;
import com.geffer.spring_university.Service.MateriaService;
import com.geffer.spring_university.Service.UsuarioService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/cursos")
public class CursoController {
    @Autowired
    private CursoService cursoService;

    @Autowired
    private MateriaService materiaService;

    @Autowired
    private UsuarioService usuarioService;

    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/cursos/".toString();
    private Path uploadDir = Paths.get(UPLOAD_DIR);

    @GetMapping("/")
    public String mostrarCursos(Model model) {
        List<Curso> curso = cursoService.obtenerTodosLosCursos();
        model.addAttribute("cursos", curso);
        return "cursos/MostrarCursos";
    }

    @GetMapping("/new")
    public String crearNuevo() {
        return ("cursos/CrearCursos");
    }

    @PostMapping("/create")
    public String guardarNuevo(@RequestParam MultipartFile foto, @ModelAttribute Curso curso) throws IOException {
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        String uniqueFileName = UUID.randomUUID().toString() + foto.getOriginalFilename().toString();
        Path destinationFile = uploadDir.resolve(uniqueFileName);
        foto.transferTo(destinationFile);
        curso.setRuta_foto("/uploads/cursos/" + uniqueFileName);
        cursoService.guardarCurso(curso);
        return "redirect:/cursos/";
    }

    @Transactional
    @GetMapping("/view/{id}")
    public String verCurso(@PathVariable("id") Long id, Model model) {
        Curso curso = cursoService.obtenerCursoPorId(id);
        Set<Usuario> estudiantes = curso.getUsuarios();
        List<Materia> materias = materiaService.obtenerLasMateriasDeUnCurso(curso);
        model.addAttribute("curso", curso);
        model.addAttribute("materias", materias);
        model.addAttribute("estudiantes", estudiantes);
        return "cursos/VerCurso";
    }

    @GetMapping("/edit/{id}")
    public String editarCurso(@PathVariable("id") Long id, Model model) {
        Curso curso = cursoService.obtenerCursoPorId(id);
        model.addAttribute("curso", curso);
        return "cursos/EditarCursos";
    }

    @PostMapping("/update/{id}")
    public String actualizarCurso(@RequestParam MultipartFile foto, @PathVariable("id") Long id, @ModelAttribute("curso") Curso curso) throws IOException {
        Curso cursoExistente = cursoService.obtenerCursoPorId(id);
        cursoExistente.setNombre_curso(curso.getNombre_curso());

        if (!foto.isEmpty()) {
            String fotoAnterior = cursoExistente.getRuta_foto();
            String rutaCompleta = "src/main/resources/static".toString() + fotoAnterior;
            java.io.File archivoAnterior = new java.io.File(rutaCompleta);
            archivoAnterior.delete();
            String uniqueFileName = foto.getOriginalFilename().replace(" ", "_");
            Path destinationFile = uploadDir.resolve(uniqueFileName);
            foto.transferTo(destinationFile);
            cursoExistente.setRuta_foto("/uploads/cursos/" + uniqueFileName);
        }

        cursoService.guardarCurso(cursoExistente);
        return "redirect:/cursos/";
    }

    @Transactional
    @PostMapping("/delete/{id}")
    public String eliminarCurso(@PathVariable("id") Long id) {
        Curso curso = cursoService.obtenerCursoPorId(id);
        String rutaCompleta = "src/main/resources/static".toString() + curso.getRuta_foto().toString();
        java.io.File archivoAnterior = new java.io.File(rutaCompleta);
        cursoService.eliminarCurso(id);
        archivoAnterior.delete();
        return "redirect:/cursos/";
    }

    @Transactional
    @PostMapping("/deleteStudent/{id}")
    public String eliminarEstudianteDelCurso(@PathVariable("id")Long usuario_id, @RequestParam Long curso_id) {
        usuarioService.eliminarUsuarioDeCurso(usuario_id, curso_id);
        Curso curso = cursoService.obtenerCursoPorId(curso_id);
        return "redirect:/cursos/view/" + curso.getId();
    }
    

}
