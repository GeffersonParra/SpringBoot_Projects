package com.geffer.spring_store.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.geffer.spring_store.DTO.OrderDetailDTO;
import com.geffer.spring_store.ServiceImpl.OrderDetailServiceImpl;

import jakarta.transaction.Transactional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("api/orderdetails")
@CrossOrigin(origins = "http://localhost:5173")
public class OrderDetailController {
    @Autowired
    private OrderDetailServiceImpl orderDetailServiceImpl;

    @Transactional
    @GetMapping
    public ResponseEntity<List<OrderDetailDTO>> getAllOrderDetails() {
        List<OrderDetailDTO> orderDetails = orderDetailServiceImpl.findAll();
        return new ResponseEntity<>(orderDetails, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAnOrderDetail(@PathVariable("id")Long id) {
        OrderDetailDTO orderDetailDTO = orderDetailServiceImpl.findById(id);
        if (orderDetailDTO == null) {
            return new ResponseEntity<>("Detalle de compra no encontrado", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(orderDetailDTO, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<OrderDetailDTO> saveAnOrderDetail(@RequestBody OrderDetailDTO orderDetailDTO) {
        OrderDetailDTO newOrderDetailDTO = orderDetailServiceImpl.save(orderDetailDTO);
        return new ResponseEntity<>(newOrderDetailDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAnOrderDetail(@PathVariable("id")Long id){
        OrderDetailDTO orderDetailDTO = orderDetailServiceImpl.findById(id);
        if (orderDetailDTO == null) {
            return new ResponseEntity<>("Detalle de compra no encontrado.", HttpStatus.NOT_FOUND);
        }
        orderDetailServiceImpl.delete(id);
        return new ResponseEntity<>("Detalle de compra eliminado con éxito.", HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAnOrderDetail(@RequestBody OrderDetailDTO orderDetailDTO, @PathVariable("id")Long id) {
        OrderDetailDTO existingOrderDetailDTO = orderDetailServiceImpl.findById(id);
        if (existingOrderDetailDTO == null) {
            return new ResponseEntity<>("Detalle de compra no encontrado.", HttpStatus.NOT_FOUND);
        }
        orderDetailDTO.setId(id);
        orderDetailServiceImpl.save(orderDetailDTO);
        return new ResponseEntity<>(orderDetailDTO, HttpStatus.OK);
    }
}
