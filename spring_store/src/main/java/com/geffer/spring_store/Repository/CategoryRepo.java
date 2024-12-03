package com.geffer.spring_store.Repository;

import com.geffer.spring_store.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Long> {
}
