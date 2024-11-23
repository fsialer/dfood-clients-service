package com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.models;

import com.fernando.ms.clients.app.dfood_clients_service.domain.models.enums.StatusCustomer;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "customers")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastname;
    private String fullName;
    private String phone;
    private String email;
    private Long userId;
    private StatusCustomer statusCustomer;
    private LocalDate createdAt;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AddressEntity> addresses;
}
