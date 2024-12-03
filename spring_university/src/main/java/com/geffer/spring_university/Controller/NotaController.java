package com.geffer.spring_university.Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.geffer.spring_university.Entity.Curso;
import com.geffer.spring_university.Entity.Materia;
import com.geffer.spring_university.Entity.Nota;
import com.geffer.spring_university.Entity.Usuario;
import com.geffer.spring_university.Service.MateriaService;
import com.geffer.spring_university.Service.NotaService;
import com.geffer.spring_university.Service.UsuarioService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("notas/")
public class NotaController {
    
    @Autowired
    private NotaService notaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MateriaService materiaService;

    @GetMapping("/view/{id}")
    public String mostrarNotas(Model model, @PathVariable("id")Long id) {
        Usuario estudiante = usuarioService.obtenerUsuariioPorId(id);
        List<Nota> notas = notaService.obtenerNotasDeUnEstudiante(estudiante);
        model.addAttribute("estudiante", estudiante);
        model.addAttribute("notas", notas);
        return "notas/MostrarNotas";
    }

    @GetMapping("/view/student/{id}")
    public String mostrarNotasEstudiante(Model model, @PathVariable("id")Long id) {
        Usuario estudiante = usuarioService.obtenerUsuariioPorId(id);
        List<Nota> notas = notaService.obtenerNotasDeUnEstudiante(estudiante);
        model.addAttribute("estudiante", estudiante);
        model.addAttribute("notas", notas);
        return "notas/MostrarNotasEstudiante";
    }

    @GetMapping("/view/materia/{id}")
    public String mostrarNotasMateria(Model model, @PathVariable("id")Long id) {
        Materia materia = materiaService.obtenerMateriaPorId(id);
        List<Nota> notas = notaService.obtenerNotasDeUnaMateria(materia);
        model.addAttribute("materia", materia);
        model.addAttribute("notas", notas);
        return "notas/MostrarNotasMateria";
    }
    
    @GetMapping("/assign/{id}")
    public String asignarNotas(Model model, @PathVariable("id")Long id) {
        Materia materia = materiaService.obtenerMateriaPorId(id);
        Curso curso = materia.getCurso();
        Set<Usuario> estudiantes = curso.getUsuarios();
        model.addAttribute("materia", materia);
        model.addAttribute("estudiantes", estudiantes);
        return "notas/AsignarNotas";
    }

    @PostMapping("/save")
    public String guardarNotas(@RequestParam("estudianteId") List<Long> estudiantes, @RequestParam("nota") List<Float> notas, @RequestParam("materiaId") Long materiaId, @RequestParam("fecha")LocalDate fecha) {
        for (int i = 0; i < estudiantes.size(); i++){
            Long estudianteId = estudiantes.get(i);
            Float nota = notas.get(i);
            Nota nuevaNota = new Nota();
            Usuario estudiante = usuarioService.obtenerUsuariioPorId(estudianteId);
            Materia materia = materiaService.obtenerMateriaPorId(materiaId);
            nuevaNota.setEstudiante(estudiante);
            nuevaNota.setNota(nota);
            nuevaNota.setMateria(materia);
            nuevaNota.setFecha(fecha);
            notaService.guardarNota(nuevaNota);
        }
        Materia materia = materiaService.obtenerMateriaPorId(materiaId);
        return "redirect:/cursos/view/" + materia.getCurso().getId();
    }
}
