package com.geffer.spring_store.Service;

import com.geffer.spring_store.DTO.ProviderDTO;
import com.geffer.spring_store.Entity.Provider;

import java.util.List;

public interface ProviderService {
    List<ProviderDTO> findAll();

    ProviderDTO findById(Long id);

    ProviderDTO save(ProviderDTO providerDTO);

    void delete(Long id);

    Provider findProviderById(Long id);
}
