package com.geffer.spring_store.Repository;

import com.geffer.spring_store.Entity.OrderDetail;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepo extends JpaRepository<OrderDetail, Long> {
}
