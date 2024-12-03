package com.geffer.spring_store.ServiceImpl;

import com.geffer.spring_store.DTO.*;
import com.geffer.spring_store.Entity.*;
import com.geffer.spring_store.Repository.OrderRepo;
import com.geffer.spring_store.Service.OrderService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepo orderRepo;

    @Lazy
    @Autowired
    private OrderDetailServiceImpl orderDetailService;

    @Lazy
    @Autowired
    private ClientServiceImpl clientService;

    @Transactional
    @Override
    public List<OrderDTO> findAll() {
        return orderRepo.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public OrderDTO findById(Long id) {
        Optional<Order> orderOptional = orderRepo.findById(id);
        return orderOptional.map(this::convertToDTO).orElse(null);
    }

    @Transactional
    @Override
    public Order findOrderById(Long id) {
        Optional<Order> order = orderRepo.findById(id);
        return order.orElse(null);
    }

    @Override
    public OrderDTO save(OrderDTO orderDTO) {
        Order order = convertToEntity(orderDTO);
        Float subtotal = (float) 0;
        for (OrderDetail orderDetail : order.getOrderDetails()) {
             subtotal += orderDetail.getSubtotal();
        }
        order.setTotal(subtotal);
        Order savedOrder = orderRepo.save(order);
        return convertToDTO(savedOrder);
    }

    @Override
    public void delete(Long id) {
        orderRepo.deleteById(id);
    }

    public List<OrderDetail> toOrderDetail(List<OrderDetailDTO> orderDetailDTOS){
        if (orderDetailDTOS == null) {
            return Collections.emptyList();
        }
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (OrderDetailDTO dto : orderDetailDTOS) {
            OrderDetail detail = orderDetailService.convertToEntity(dto);
            orderDetails.add(detail);
        }
        return orderDetails;
    }

    OrderDTO convertToDTO(Order order){
        if (order == null){
            return null;
        }

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        List<OrderDetailDTO> orderDetailDTOS = order.getOrderDetails().stream()
                .map(orderDetailService::convertToDTO)
                .collect(Collectors.toList());
        orderDTO.setClientName(order.getId_client().getName());
        orderDTO.setOrderDetails(orderDetailDTOS);
        orderDTO.setDate(order.getDate());
        orderDTO.setTotal(order.getTotal());
        orderDTO.setClientId(order.getId_client().getId());
        return orderDTO;
    }

    public Order convertToEntity(OrderDTO orderDTO){
        if(orderDTO == null){
            return null;
        }
        Order order = new Order();
        order.setId(orderDTO.getId());
        Client client = clientService.findClient(orderDTO.getClientId());
        order.setId_client(client);
        order.setTotal(orderDTO.getTotal());
        order.setDate(orderDTO.getDate());
        order.setOrderDetails(toOrderDetail(orderDTO.getOrderDetails()));
        return order;
    }
}
