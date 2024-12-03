package com.geffer.spring_store.Controller;

import com.geffer.spring_store.DTO.ClientDTO;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.geffer.spring_store.ServiceImpl.ClientServiceImpl;

import jakarta.transaction.Transactional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("api/clients")
@CrossOrigin(origins = "http://localhost:5173")
public class ClientController {
    @Autowired 
    private ClientServiceImpl clientServiceImpl;

    @Transactional
    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        List<ClientDTO> clients = clientServiceImpl.findAll();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }
    
    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity<Object> getAClient(@PathVariable("id")Long id) {
        ClientDTO clientDTO = clientServiceImpl.findById(id);
        if (clientDTO == null) {
            return new ResponseEntity<>("Cliente no encontrado", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(clientDTO, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<ClientDTO> saveClient(@RequestBody ClientDTO clientDTO) {
        ClientDTO newClientDTO = clientServiceImpl.save(clientDTO);
        return new ResponseEntity<ClientDTO>(newClientDTO, HttpStatus.CREATED);
    }
    
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable("id")Long id){
        ClientDTO clientDTO = clientServiceImpl.findById(id);
        if (clientDTO == null) {
            return new ResponseEntity<>("Cliente no encontrado", HttpStatus.NOT_FOUND);
        }
        clientServiceImpl.delete(id);
        return new ResponseEntity<>("Cliente eliminado con éxito", HttpStatus.OK);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateClient(@PathVariable Long id, @RequestBody ClientDTO clientDTO) {
        ClientDTO existingClientDTO = clientServiceImpl.findById(id);
        if (existingClientDTO == null) {
            return new ResponseEntity<>("CLiente no encontrado.", HttpStatus.NOT_FOUND);
        }
        clientDTO.setId(id);
        ClientDTO updateClient = clientServiceImpl.save(clientDTO);
        return new ResponseEntity<>(updateClient, HttpStatus.OK);
    }
}