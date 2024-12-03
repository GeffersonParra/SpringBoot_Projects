package com.geffer.spring_store.ServiceImpl;

import com.geffer.spring_store.DTO.CategoryDTO;
import com.geffer.spring_store.DTO.ProductDTO;
import com.geffer.spring_store.Entity.Category;
import com.geffer.spring_store.Entity.Product;
import com.geffer.spring_store.Repository.CategoryRepo;
import com.geffer.spring_store.Service.CategoryService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;

    @Lazy
    @Autowired
    private ProductServiceImpl productService;

    @Override
    @Transactional
    public List<CategoryDTO> findAll() {
        return categoryRepo.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO findById(Long id) {
        Optional<Category> categoryOptional = categoryRepo.findById(id);
        return categoryOptional.map(this::convertToDTO).orElse(null);
    }

    @Override
    public Category findCategoryById(Long id) {
        return categoryRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
    }

    @Override
    public CategoryDTO save(CategoryDTO categoryDTO) {
        Category category = convertToEntity(categoryDTO);
        Category savedCategory = categoryRepo.save(category);
        return convertToDTO(savedCategory);
    }

    @Override
    public void delete(Long id) {
        categoryRepo.deleteById(id);
    }

    public Set<Product> toProduct(Set<ProductDTO> productDTOS) {
        if (productDTOS == null) {
            return null;
        }
        Set<Product> products = new HashSet<>();
        for (ProductDTO dto : productDTOS) {
            Product product = productService.convertToEntity(dto);
            products.add(product);
        }
        return products;
    }

    public CategoryDTO convertToDTO(Category category) {
        if (category == null) {
            return null;
        }
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setDescription(category.getDescription());
        Set<ProductDTO> productDTOs = new HashSet<>(category.getProducts().stream()
                .map(productService::convertToDTO)
                .collect(Collectors.toList()));
        categoryDTO.setProducts(productDTOs);
        return categoryDTO;
    }

    public Category convertToEntity(CategoryDTO categoryDTO) {
        if (categoryDTO == null) {
            return null;
        }
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setDescription(categoryDTO.getDescription());
        category.setName(categoryDTO.getName());
        category.setProducts(toProduct(categoryDTO.getProducts()));
        return category;
    }
}
