package com.geffer.spring_store.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Categorías")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Nombre", length = 30, nullable = false)
    private String name;

    @Column(name = "Descripción", length = 300)
    private String description;

    @OneToMany(mappedBy = "category")
    private Set<Product> products = new HashSet<>();
}
