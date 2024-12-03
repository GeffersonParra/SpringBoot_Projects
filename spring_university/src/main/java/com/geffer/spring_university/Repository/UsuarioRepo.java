package com.geffer.spring_university.Repository;

import com.geffer.spring_university.Entity.Rol;
import com.geffer.spring_university.Entity.Usuario;
import org.springframework.data.repository.CrudRepository;
import java.util.List;


public interface UsuarioRepo extends CrudRepository<Usuario, Long> {
    List<Usuario> findByRol(Rol rol);
}
