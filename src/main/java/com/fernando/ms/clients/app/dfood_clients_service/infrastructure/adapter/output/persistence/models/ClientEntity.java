package com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.models;

import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Address;
import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Client;
import com.fernando.ms.clients.app.dfood_clients_service.domain.models.enums.StatusClient;
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
@Table(name = "clients")
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastname;
    private String fullName;
    private String phone;
    private String email;
    private Long userId;
    private StatusClient statusClient;
    private LocalDate createdAt;
    @OneToMany
    private List<AddressEntity> addresses;
}
