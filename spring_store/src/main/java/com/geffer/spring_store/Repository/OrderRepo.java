package com.geffer.spring_store.Repository;

import com.geffer.spring_store.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepo extends JpaRepository<Order, Long> {
    
}
