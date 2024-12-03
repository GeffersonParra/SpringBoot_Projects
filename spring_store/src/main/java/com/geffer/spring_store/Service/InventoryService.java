package com.geffer.spring_store.Service;

import com.geffer.spring_store.DTO.InventoryDTO;

import java.util.List;

public interface InventoryService {
    List<InventoryDTO> findAll();

    InventoryDTO findById(Long id);

    InventoryDTO save(InventoryDTO inventoryDTO);

    void delete(Long id);
}
