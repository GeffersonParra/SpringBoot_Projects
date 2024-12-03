package com.geffer.spring_store.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.geffer.spring_store.DTO.OrderDTO;
import com.geffer.spring_store.ServiceImpl.OrderServiceImpl;

import jakarta.transaction.Transactional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("api/orders")
@CrossOrigin(origins = "http://localhost:5173")
public class OrderController {
    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @Transactional
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orders = orderServiceImpl.findAll();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
    
    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity<Object> getAnOrder(@PathVariable("id")Long id) {
        OrderDTO orderDTO = orderServiceImpl.findById(id);
        if (orderDTO == null) {
            return new ResponseEntity<>("Orden no encontrada.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(orderDTO, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<OrderDTO> saveOrder(@RequestBody OrderDTO orderDTO) {
        OrderDTO newOrderDTO = orderServiceImpl.save(orderDTO);
        return new ResponseEntity<OrderDTO>(newOrderDTO, HttpStatus.CREATED);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable("id")Long id){
        OrderDTO orderDTO = orderServiceImpl.findById(id);
        if (orderDTO == null) {
            return new ResponseEntity<>("Orden no encontrada.", HttpStatus.NOT_FOUND);
        }
        orderServiceImpl.delete(id);
        return new ResponseEntity<>("Orden eliminada con éxito.", HttpStatus.OK);
    }
    
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateOrder(@PathVariable("id")Long id, @RequestBody OrderDTO orderDTO) {
        OrderDTO existingOrderDto = orderServiceImpl.findById(id);
        if (existingOrderDto == null) {
            return new ResponseEntity<>("Orden no encontrada.", HttpStatus.NOT_FOUND);
        }
        orderDTO.setId(id);
        orderServiceImpl.save(orderDTO);
        return new ResponseEntity<>(orderDTO, HttpStatus.OK);
    }
}
