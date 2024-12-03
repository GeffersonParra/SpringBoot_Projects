package com.geffer.spring_store.Controller;

import com.geffer.spring_store.DTO.ProviderDTO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.geffer.spring_store.ServiceImpl.ProviderServiceImpl;

import jakarta.transaction.Transactional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("api/providers")
@CrossOrigin(origins = "http://localhost:5173")
public class ProviderController {
    @Autowired
    private ProviderServiceImpl providerServiceImpl;

    @Transactional
    @GetMapping
    public ResponseEntity<List<ProviderDTO>> getAllProviders() {
        List<ProviderDTO> providers = providerServiceImpl.findAll();
        return new ResponseEntity<>(providers, HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity<Object> getAProvider(@PathVariable("id")Long id) {
        ProviderDTO provider = providerServiceImpl.findById(id);
        if (provider == null){
            return new ResponseEntity<>("Proveedor no encontrado.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(provider, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<ProviderDTO> saveProvider(@RequestBody ProviderDTO providerDTO) {
        ProviderDTO newProviderDTO = providerServiceImpl.save(providerDTO);
        return new ResponseEntity<>(newProviderDTO, HttpStatus.CREATED);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProvider(@PathVariable("id")Long id){
        ProviderDTO providerDTO = providerServiceImpl.findById(id);
        if (providerDTO == null) {
            return new ResponseEntity<>("Proveedor no encontrado.", HttpStatus.NOT_FOUND);
        }
        providerServiceImpl.delete(id);
        return new ResponseEntity<>("Proveedor eliminado con éxito.", HttpStatus.OK);
    }
    
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProvider(@PathVariable Long id, @RequestBody ProviderDTO providerDTO) {
        ProviderDTO existingProvider = providerServiceImpl.findById(id);
        if(existingProvider == null){
            return new ResponseEntity<>("El proveedor solicitado NO existe", HttpStatus.NOT_FOUND);
        }
        providerDTO.setId(id);
        ProviderDTO updateProvider = providerServiceImpl.save(providerDTO);
        return new ResponseEntity<>(updateProvider, HttpStatus.OK);
    }
}
