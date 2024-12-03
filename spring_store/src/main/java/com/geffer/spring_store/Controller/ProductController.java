package com.geffer.spring_store.Controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.geffer.spring_store.DTO.CategoryDTO;
import com.geffer.spring_store.DTO.ProductDTO;
import com.geffer.spring_store.DTO.ProviderDTO;
import com.geffer.spring_store.ServiceImpl.CategoryServiceImpl;
import com.geffer.spring_store.ServiceImpl.ProductServiceImpl;
import com.geffer.spring_store.ServiceImpl.ProviderServiceImpl;

import ch.qos.logback.core.model.Model;
import jakarta.transaction.Transactional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("api/products")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {
    @Autowired
    private ProductServiceImpl productServiceImpl;

    @Transactional
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productServiceImpl.findAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
    

    @Transactional
    @GetMapping("/category/{id}")
    public ResponseEntity<List<ProductDTO>> getAllProductsOfACategory(@PathVariable("id")Long id) {
        List<ProductDTO> products = productServiceImpl.findAllProductsByCategory(id);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity<Object> getAProduct(@PathVariable("id")Long id) {
        ProductDTO productDTO = productServiceImpl.findById(id);
        if (productDTO == null) {
            return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }
    
    @Transactional
    @PostMapping
    public ResponseEntity<ProductDTO> saveProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO newProduct = productServiceImpl.save(productDTO);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id){
        ProductDTO productDTO = productServiceImpl.findById(id);
        if (productDTO == null) {
            return new ResponseEntity<>("Producto no encontrado.", HttpStatus.NOT_FOUND);
        }
        productServiceImpl.delete(id);
        return new ResponseEntity<>("Producto eliminado exitosamente.", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable("id")Long id, @RequestBody ProductDTO productDTO) {
        ProductDTO existingProductDTO = productServiceImpl.findById(id);
        if (existingProductDTO == null) {
            return new ResponseEntity<>("Producto no encontrado", HttpStatus.NOT_FOUND);
        }
        productDTO.setId(id);
        productServiceImpl.save(productDTO);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }
}
