package com.geffer.spring_store.Service;

import com.geffer.spring_store.DTO.ClientDTO;


import java.util.List;

public interface ClientService {
    List<ClientDTO> findAll();

    ClientDTO findById(Long id);

    ClientDTO save(ClientDTO clientDTO);

    void delete(Long id);
}
