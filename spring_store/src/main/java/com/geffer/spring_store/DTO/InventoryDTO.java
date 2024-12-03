package com.geffer.spring_store.DTO;

import com.geffer.spring_store.Entity.Product;

public class InventoryDTO {
    private Long id;
    private Product product;
    private Integer stock;

    public InventoryDTO(Long id, Product product, Integer stock) {
        this.id = id;
        this.product = product;
        this.stock = stock;
    }

    public InventoryDTO(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
