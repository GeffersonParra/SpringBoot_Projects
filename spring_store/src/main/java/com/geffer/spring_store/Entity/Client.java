package com.geffer.spring_store.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Clientes")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Nombre", length = 100, nullable = false)
    private String name;

    @Column(name = "Teléfono", length = 15)
    private String tel_number;

    @Column(name = "Correo", length = 50)
    private String email;

    @Column(name = "Dirección")
    private String address;

    @OneToMany(mappedBy = "id_client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;
}
