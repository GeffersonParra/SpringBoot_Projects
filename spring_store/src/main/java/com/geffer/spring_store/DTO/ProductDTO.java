package com.geffer.spring_store.DTO;

public class ProductDTO {
    private Long id;
    private String name;
    private Float price;
    private Long id_provider;
    private String provider_name;
    private Long id_category;
    private String category_name;
    private Integer quantity;

    public ProductDTO(Long id, String name, Float price, Long id_provider, String provider_name, Long id_category, String category_name, Integer quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.id_provider = id_provider;
        this.provider_name = provider_name;
        this.id_category = id_category;
        this.category_name = category_name;
        this.quantity = quantity;
    }

    public ProductDTO(){};

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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Long getId_provider() {
        return id_provider;
    }

    public void setId_provider(Long id_provider) {
        this.id_provider = id_provider;
    }

    public String getProvider_name() {
        return provider_name;
    }

    public void setProvider_name(String provider_name) {
        this.provider_name = provider_name;
    }

    public Long getId_category() {
        return id_category;
    }

    public void setId_category(Long id_category) {
        this.id_category = id_category;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}