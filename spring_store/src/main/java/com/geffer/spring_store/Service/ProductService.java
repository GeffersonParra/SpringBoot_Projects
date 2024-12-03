package com.geffer.spring_store.Service;

import com.geffer.spring_store.DTO.ProductDTO;
import com.geffer.spring_store.Entity.Product;

import java.util.List;

public interface ProductService {
    public List<ProductDTO> findAll();

    public List<ProductDTO> findAllProductsByCategory(Long id);

    ProductDTO findById(Long id);

    Product findProductById(Long id);

    ProductDTO save(ProductDTO productDTO);

    void delete(Long id);
}
