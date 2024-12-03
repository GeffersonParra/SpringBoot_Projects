package com.geffer.spring_store.DTO;

import java.time.LocalDate;
import java.util.List;

public class OrderDTO {
    private Long id;
    private LocalDate date = LocalDate.now();
    private Float total;
    private Long clientId;
    private String clientName;
    private List<OrderDetailDTO> orderDetails;

    public OrderDTO(Long id, LocalDate date, Float total, Long clientId, String clientName, List<OrderDetailDTO> orderDetails) {
        this.id = id;
        this.date = date;
        this.total = total;
        this.clientId = clientId;
        this.clientName = clientName;
        this.orderDetails = orderDetails;
    }

    public  OrderDTO(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public List<OrderDetailDTO> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailDTO> orderDetails) {
        this.orderDetails = orderDetails;
    }
}

