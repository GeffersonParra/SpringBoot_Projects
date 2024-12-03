package com.geffer.spring_store.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Detalles_Compra")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Order order;

    @ManyToOne
    private Product product;

    @Column(name = "Cantidad", nullable = false)
    private Integer quantity;

    @Column(name = "Subtotal", nullable = false)
    private Float subtotal;
}
