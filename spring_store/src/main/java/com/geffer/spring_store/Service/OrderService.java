package com.geffer.spring_store.Service;


import com.geffer.spring_store.DTO.OrderDTO;
import com.geffer.spring_store.Entity.Order;

import java.util.List;

public interface OrderService {
    List<OrderDTO> findAll();

    OrderDTO findById(Long id);

    Order findOrderById(Long id);

    OrderDTO save(OrderDTO orderDTO);

    void delete(Long id);
}
