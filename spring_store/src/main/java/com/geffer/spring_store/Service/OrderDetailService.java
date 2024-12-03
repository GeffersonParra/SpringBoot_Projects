package com.geffer.spring_store.Service;

import com.geffer.spring_store.DTO.OrderDetailDTO;

import java.util.List;

public interface OrderDetailService {
    List<OrderDetailDTO> findAll();

    OrderDetailDTO findById(Long id);

    OrderDetailDTO save(OrderDetailDTO orderDetailDTO);

    void delete(Long id);
}
