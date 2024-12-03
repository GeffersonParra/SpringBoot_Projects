package com.geffer.spring_store.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Proveedores")
public class Provider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Nombre", length = 50, nullable = false)
    private String name;

    @Column(name = "Teléfono", length = 12)
    private String tel_number;

    @Column(name = "Correo", length = 50)
    private String email;

    @Column(name = "Dirección", length = 50)
    private String address;

    @OneToMany(mappedBy = "id_provider")
    private List<Product> products;
}
