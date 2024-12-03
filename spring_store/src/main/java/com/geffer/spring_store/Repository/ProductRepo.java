package com.geffer.spring_store.Repository;

import com.geffer.spring_store.Entity.Category;
import com.geffer.spring_store.Entity.Product;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);
}
