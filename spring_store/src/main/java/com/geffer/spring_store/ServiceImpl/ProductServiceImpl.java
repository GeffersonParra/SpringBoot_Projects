package com.geffer.spring_store.ServiceImpl;

import com.geffer.spring_store.DTO.ProductDTO;
import com.geffer.spring_store.Entity.*;
import com.geffer.spring_store.Repository.ProductRepo;
import com.geffer.spring_store.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    CategoryServiceImpl categoryServiceImpl;

    @Lazy
    @Autowired
    ProviderServiceImpl providerServiceImpl;

    
    @Autowired
    OrderServiceImpl orderService;

    @Override
    public List<ProductDTO> findAll() {
        return productRepo.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> findAllProductsByCategory(Long id) {
        Category category = categoryServiceImpl.findCategoryById(id);
        return productRepo.findByCategory(category).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public ProductDTO findById(Long id) {
        Optional<Product> productOptional = productRepo.findById(id);
        return productOptional.map(this::convertToDTO).orElse(null);
    }

    @Override
    public Product findProductById(Long id) {
        Optional<Product> product = productRepo.findById(id);
        return product.orElse(null);
    }

    @Override
    public ProductDTO save(ProductDTO productDTO) {
        Category category = categoryServiceImpl.findCategoryById(productDTO.getId_category());
        Provider provider = providerServiceImpl.findProviderById(productDTO.getId_provider());
        Product product = convertToEntity(productDTO);
        product.setCategory(category);
        product.setId_provider(provider);
        Product savedProduct = productRepo.save(product);
        return convertToDTO(savedProduct);
    }

    @Override
    public void delete(Long id) {
        productRepo.deleteById(id);
    }

    public List<Product> toProduct(List<ProductDTO> productDTOS){
        if (productDTOS == null) {
            return Collections.emptyList();
        }
        List<Product> products = new ArrayList<>();
        for (ProductDTO dto : productDTOS) {
            Product product = convertToEntity(dto);
            products.add(product);
        }
        return products;
    }

    public ProductDTO convertToDTO(Product product) {
        if (product == null) {
            return null;
        }
        
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        
        if (product.getCategory() != null) {
            productDTO.setId_category(product.getCategory().getId());
            productDTO.setCategory_name(product.getCategory().getName());  
        }
        
        if (product.getId_provider() != null) {
            productDTO.setId_provider(product.getId_provider().getId());
            productDTO.setProvider_name(product.getId_provider().getName()); 
        }

        productDTO.setPrice(product.getPrice());
        productDTO.setQuantity(product.getQuantity());
        return productDTO;
    }
    
    
    public Product convertToEntity(ProductDTO productDTO) {
        if (productDTO == null) {
            return null;
        }
    
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
    
        return product;
    }
}