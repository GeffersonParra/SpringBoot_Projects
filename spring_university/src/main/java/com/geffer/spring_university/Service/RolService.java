package com.geffer.spring_university.Service;

import com.geffer.spring_university.Entity.Rol;
import com.geffer.spring_university.Repository.RolRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolService {
    @Autowired
    private RolRepo rolRepository;

    public List<Rol> obtenerTodosLosRoles(){
        return (List<Rol>) rolRepository.findAll();
    }

    public Rol obtenerRolPorId(Long id){
        return rolRepository.findById(id).get();
    }

    public Rol guardarRol(Rol rol){
        return rolRepository.save(rol);
    }

    public void eliminarRol(Long id){
        rolRepository.deleteById(id);
    }
}
