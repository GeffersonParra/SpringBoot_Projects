package com.geffer.spring_store.Service;

import com.geffer.spring_store.DTO.CategoryDTO;

import java.util.List;
import com.geffer.spring_store.Entity.Category;

public interface CategoryService {

    List<CategoryDTO> findAll();

    CategoryDTO findById(Long id);

    CategoryDTO save(CategoryDTO categoryDTO);

    void delete(Long id);

    Category findCategoryById(Long id);
}
