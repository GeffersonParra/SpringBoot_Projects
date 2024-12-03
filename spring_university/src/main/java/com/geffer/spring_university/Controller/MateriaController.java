package com.geffer.spring_university.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.geffer.spring_university.Entity.Curso;
import com.geffer.spring_university.Entity.Materia;
import com.geffer.spring_university.Entity.Rol;
import com.geffer.spring_university.Entity.Usuario;
import com.geffer.spring_university.Service.CursoService;
import com.geffer.spring_university.Service.MateriaService;
import com.geffer.spring_university.Service.RolService;
import com.geffer.spring_university.Service.UsuarioService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.time.LocalTime;



@Controller
@RequestMapping("/materias")
public class MateriaController {
    @Autowired
    private MateriaService materiaService;

    @Autowired
    private CursoService cursoService;

    @Autowired
    private RolService rolService;

    @Autowired
    private UsuarioService usuarioService;

    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/materias/".toString();
    private Path uploadDir = Paths.get(UPLOAD_DIR);

    @GetMapping("/new/{id}")
    public String crearMateria(@PathVariable("id")Long curso_id, Model model) {
        Curso curso = cursoService.obtenerCursoPorId(curso_id);
        model.addAttribute("curso", curso);
        List<Curso> cursos = cursoService.obtenerTodosLosCursos();
        model.addAttribute("cursos", cursos);
        Rol rol = rolService.obtenerRolPorId((long) 1);
        List<Usuario> profesores = usuarioService.obtenerTipoUsuario(rol);
        model.addAttribute("profesores", profesores);
        return "materias/CrearMateria";
    }
    
    @PostMapping("/create")
    public String guardarNuevo(@RequestParam MultipartFile foto, @RequestParam String hora_inicio, @RequestParam String hora_fin,  @ModelAttribute Materia materia) throws IOException {
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        String uniqueFileName = UUID.randomUUID().toString() + foto.getOriginalFilename().toString();
        Path destinationFile = uploadDir.resolve(uniqueFileName);
        foto.transferTo(destinationFile);
        materia.setRuta_foto("/uploads/materias/" + uniqueFileName);
        materia.setHoraInicio(LocalTime.parse(hora_inicio));
        materia.setHoraFin(LocalTime.parse(hora_fin));
        materiaService.guardarMateria(materia);
        Long cursoId = materia.getCurso().getId();
        return "redirect:/cursos/view/" + cursoId;
    }

    @GetMapping("/edit/{id}")
    public String editarMateria(@PathVariable("id")Long id, Model model) {
        Materia materia = materiaService.obtenerMateriaPorId(id);
        model.addAttribute("materia", materia);
        List<Curso> cursos = cursoService.obtenerTodosLosCursos();
        model.addAttribute("cursos", cursos);
        Rol rol = rolService.obtenerRolPorId((long) 1);
        List<Usuario> profesores = usuarioService.obtenerTipoUsuario(rol);
        model.addAttribute("profesores", profesores);
        return "materias/EditarMateria";
    }
    
    @PostMapping("/update/{id}")
    public String actualizarMateria(@RequestParam MultipartFile foto, @PathVariable("id") Long id, @ModelAttribute("materia") Materia materia) throws IOException {
        Materia materiaExistente = materiaService.obtenerMateriaPorId(id);
        materiaExistente.setNombre_materia(materia.getNombre_materia());
        materiaExistente.setCurso(materia.getCurso());
        materiaExistente.setHoraInicio(materia.getHoraInicio());
        materiaExistente.setHoraFin(materia.getHoraFin());
        materiaExistente.setId_profesor(materia.getId_profesor());

        if (!foto.isEmpty()) {
            String fotoAnterior = materiaExistente.getRuta_foto();
            String rutaCompleta = "src/main/resources/static".toString() + fotoAnterior;
            java.io.File archivoAnterior = new java.io.File(rutaCompleta);
            archivoAnterior.delete();
            String uniqueFileName = foto.getOriginalFilename().replace(" ", "_");
            Path destinationFile = uploadDir.resolve(uniqueFileName);
            foto.transferTo(destinationFile);
            materiaExistente.setRuta_foto("/uploads/materias/" + uniqueFileName);
        }

        materiaService.guardarMateria(materiaExistente);
        Long cursoId = materia.getCurso().getId();
        return "redirect:/cursos/view/" + cursoId;
    }
    
    @PostMapping("/delete/{id}")
    public String eliminarMateria(@PathVariable("id") Long id, Model model) {
        Materia materia = materiaService.obtenerMateriaPorId(id);
        Long cursoId = materia.getCurso().getId();
        String rutaCompleta = "src/main/resources/static".toString() + materia.getRuta_foto().toString();
        java.io.File archivoAnterior = new java.io.File(rutaCompleta);
        materiaService.eliminarMateria(id);
        archivoAnterior.delete();
        return "redirect:/cursos/view/" + cursoId;
    }

    @GetMapping("/")
    public String mostrarTodasLasMaterias(Model model) {
        List<Materia> materias = materiaService.obtenerTodasLasMaterias();
        model.addAttribute("materias", materias);
        return "materias/MostrarMaterias";
    }
    
    
}
