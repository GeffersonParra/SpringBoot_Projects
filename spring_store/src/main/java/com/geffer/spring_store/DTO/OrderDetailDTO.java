package com.geffer.spring_store.DTO;

public class OrderDetailDTO {
    private Long id;
    private Long orderId;
    private Long productId;
    private String product_name;
    private Float product_price;
    private Integer quantity;
    private Float subtotal;

    public OrderDetailDTO(Long id, Long orderId, Long productId, String product_name, Float product_price, Integer quantity, Float subtotal) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.product_name = product_name;
        this.product_price = product_price;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }

    public OrderDetailDTO(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public Float getProduct_price() {
        return product_price;
    }

    public void setProduct_price(Float product_price) {
        this.product_price = product_price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Float subtotal) {
        this.subtotal = subtotal;
    }
}
