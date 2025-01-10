package com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "customer_user")
public class CustomerUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private Long userId;
    @OneToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;
}
