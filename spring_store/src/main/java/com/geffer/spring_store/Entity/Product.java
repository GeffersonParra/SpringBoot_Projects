package com.geffer.spring_store.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Productos")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Nombre", length = 50, nullable = false)
    private String name;

    @Column(name = "Precio", nullable = false)
    private Float price;

    @ManyToOne
    private Provider id_provider;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "Cantidad", nullable = false)
    private Integer quantity;
}
