package com.geffer.spring_store.Controller;

import com.geffer.spring_store.DTO.CategoryDTO;
import com.geffer.spring_store.DTO.ProductDTO;
import com.geffer.spring_store.ServiceImpl.CategoryServiceImpl;
import com.geffer.spring_store.ServiceImpl.ProductServiceImpl;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:5173")
public class CategoryController {
    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private ProductServiceImpl productServiceImpl;

    @Transactional
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories(){
        List<CategoryDTO> categories = categoryService.findAll();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity<List<ProductDTO>> getAllProductsOfACategory(@PathVariable("id")Long id) {
        List<ProductDTO> products = productServiceImpl.findAllProductsByCategory(id);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/view/{id}")
    public ResponseEntity<Object> getACategory(@PathVariable("id")Long id) {
        CategoryDTO categoryDTO = categoryService.findById(id);
        if (categoryDTO == null) {
            return new ResponseEntity<>("No ha sido posible encontrar la categoría.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> saveCategory (@RequestBody CategoryDTO categoryDTO) {
        CategoryDTO newCategoryDTO = categoryService.save(categoryDTO);
        return new ResponseEntity<>(newCategoryDTO, HttpStatus.CREATED);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory (@PathVariable("id") Long id){
        CategoryDTO categoryDTO = categoryService.findById(id);
        if (categoryDTO == null){
            return new ResponseEntity<>("Categoría no encontrada", HttpStatus.NOT_FOUND);
        }
        categoryService.delete(id);
        return new ResponseEntity<>("Categoría eliminada con éxito.", HttpStatus.OK);
    }
    
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO existingCategoryDTO = categoryService.findById(id);
        if (existingCategoryDTO == null) {
            return new ResponseEntity<>("La categoría solicitada no existe.", HttpStatus.NOT_FOUND);
        }
        categoryDTO.setId(id);
        CategoryDTO updateCategory = categoryService.save(categoryDTO);
        return new ResponseEntity<>(updateCategory, HttpStatus.OK);
    }
}
