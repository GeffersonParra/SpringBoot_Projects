package com.geffer.spring_store.DTO;

import java.util.List;
import java.util.Set;

public class ClientDTO {
    private Long id;
    private String name;
    private String tel_number;
    private String email;
    private String address;
    private List<OrderDTO> orders;

    public ClientDTO(Long id, String name, String tel_number, String email, String address, List<OrderDTO> orders) {
        this.id = id;
        this.name = name;
        this.tel_number = tel_number;
        this.email = email;
        this.address = address;
        this.orders = orders;
    }

    public ClientDTO(){}

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

    public String getTel_number() {
        return tel_number;
    }

    public void setTel_number(String tel_number) {
        this.tel_number = tel_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<OrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDTO> orders) {
        this.orders = orders;
    }
}
