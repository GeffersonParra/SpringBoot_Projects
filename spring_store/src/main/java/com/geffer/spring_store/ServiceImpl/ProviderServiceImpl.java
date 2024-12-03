package com.geffer.spring_store.ServiceImpl;

import com.geffer.spring_store.DTO.ProductDTO;
import com.geffer.spring_store.DTO.ProviderDTO;
import com.geffer.spring_store.Entity.Provider;
import com.geffer.spring_store.Repository.ProviderRepo;
import com.geffer.spring_store.Service.ProviderService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProviderServiceImpl implements ProviderService {
    @Autowired
    private ProviderRepo providerRepo;

   
    @Autowired
    private ProductServiceImpl productService;

    @Transactional
    @Override
    public List<ProviderDTO> findAll() {
        return providerRepo.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ProviderDTO findById(Long id) {
        Optional<Provider> optionalProvider = providerRepo.findById(id);
        return optionalProvider.map(this::convertToDTO).orElse(null);
    }

    @Override
    public Provider findProviderById(Long id) {
        return providerRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Provider not found with id: " + id));
    }

    @Override
    public ProviderDTO save(ProviderDTO providerDTO) {
        Provider provider = convertToEntity(providerDTO);
        Provider savedProvider = providerRepo.save(provider);
        return convertToDTO(savedProvider);
    }

    @Override
    public void delete(Long id) {
        providerRepo.deleteById(id);
    }

    public ProviderDTO convertToDTO(Provider provider){
        if (provider == null){
            return null;
        }

        ProviderDTO providerDTO = new ProviderDTO();
        providerDTO.setId(provider.getId());
        providerDTO.setName(provider.getName());
        providerDTO.setAddress(provider.getAddress());
        providerDTO.setEmail(provider.getEmail());
        List<ProductDTO> productDTOS = provider.getProducts().stream()
                .map(productService::convertToDTO)
                .collect(Collectors.toList());
        providerDTO.setProducts(productDTOS);
        providerDTO.setTel_number(provider.getTel_number());
        return providerDTO;
    }

    public Provider convertToEntity(ProviderDTO providerDTO){
        if(providerDTO == null){
            return null;
        }

        Provider provider = new Provider();
        provider.setId(providerDTO.getId());
        provider.setName(providerDTO.getName());
        provider.setEmail(providerDTO.getEmail());
        provider.setAddress(providerDTO.getAddress());
        provider.setTel_number(providerDTO.getTel_number());
        provider.setProducts(productService.toProduct(providerDTO.getProducts()));
        return provider;
    }
}
