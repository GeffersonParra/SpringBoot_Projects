package com.geffer.spring_store.DTO;

import java.util.Set;

public class CategoryDTO {
    private Long id;
    private String name;
    private String description;
    private Set<ProductDTO> products;

    public CategoryDTO(Long id, String name, String description, Set<ProductDTO> products) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.products = products;
    }

    public CategoryDTO(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductDTO> products) {
        this.products = products;
    }
}