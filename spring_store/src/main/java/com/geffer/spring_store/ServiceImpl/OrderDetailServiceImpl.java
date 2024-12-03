package com.geffer.spring_store.ServiceImpl;

import com.geffer.spring_store.DTO.OrderDTO;
import com.geffer.spring_store.DTO.OrderDetailDTO;
import com.geffer.spring_store.Entity.Order;
import com.geffer.spring_store.Entity.OrderDetail;
import com.geffer.spring_store.Entity.Product;
import com.geffer.spring_store.Repository.OrderDetailRepo;
import com.geffer.spring_store.Service.OrderDetailService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private OrderDetailRepo orderDetailRepo;

    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @Autowired
    private ProductServiceImpl productServiceImpl;

    @Transactional
    @Override
    public List<OrderDetailDTO> findAll() {
        return orderDetailRepo.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public OrderDetailDTO findById(Long id) {
        Optional<OrderDetail> optionalOrderDetail = orderDetailRepo.findById(id);
        return optionalOrderDetail.map(this::convertToDTO).orElse(null);
    }

    @Override
    public OrderDetailDTO save(OrderDetailDTO orderDetailDTO) {
        OrderDetail order = convertToEntity(orderDetailDTO);
        OrderDetail savedOrderDetail = orderDetailRepo.save(order);
        actualizarTotalOrden(orderDetailDTO.getOrderId());
        return convertToDTO(savedOrderDetail);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        OrderDetail orderDetail = orderDetailRepo.findById(id).orElse(null);
        if (orderDetail != null) {
            Long orderId = orderDetail.getOrder().getId();
            Order order = orderDetail.getOrder();

            order.getOrderDetails().remove(orderDetail);

            orderServiceImpl.save(orderServiceImpl.convertToDTO(order));

            actualizarTotalOrden(orderId);
        } else {
            System.out.println("El detalle de la orden no existe con el ID proporcionado.");
        }
    }

    @Transactional
    OrderDetailDTO convertToDTO(OrderDetail orderDetail) {
        if (orderDetail == null) {
            return null;
        }

        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
        orderDetailDTO.setId(orderDetail.getId());
        orderDetailDTO.setProductId(orderDetail.getProduct().getId());
        orderDetailDTO.setQuantity(orderDetail.getQuantity());
        orderDetailDTO.setSubtotal(orderDetail.getSubtotal());
        orderDetailDTO.setProduct_name(orderDetail.getProduct().getName());
        orderDetailDTO.setProduct_price(orderDetail.getProduct().getPrice());
        if (orderDetail.getOrder() != null && orderDetail.getOrder().getId() != null) {
            orderDetailDTO.setOrderId(orderDetail.getOrder().getId());
        }
        return orderDetailDTO;
    }

    public OrderDetail convertToEntity(OrderDetailDTO orderDetailDTO) {
        if (orderDetailDTO == null) {
            return null;
        }
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setId(orderDetailDTO.getId());
        Order order = orderServiceImpl.findOrderById(orderDetailDTO.getOrderId());
        orderDetail.setOrder(order);
        Product product = productServiceImpl.findProductById(orderDetailDTO.getProductId());
        orderDetail.setProduct(product);
        orderDetail.setSubtotal(orderDetailDTO.getSubtotal());
        orderDetail.setQuantity(orderDetailDTO.getQuantity());
        return orderDetail;
    }

    @Transactional
    public void actualizarTotalOrden(Long orderId) {
        Order order = orderServiceImpl.findOrderById(orderId);
        List<OrderDetail> detalles = order.getOrderDetails();
        float total = 0;
        for (OrderDetail detalle : detalles) {
            total += detalle.getSubtotal();
        }
        order.setTotal(total);
        OrderDTO orderDTO = orderServiceImpl.convertToDTO(order);
        orderServiceImpl.save(orderDTO);
    }
}