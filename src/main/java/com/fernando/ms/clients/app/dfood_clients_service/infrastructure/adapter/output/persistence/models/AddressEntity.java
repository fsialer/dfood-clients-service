package com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "addresses")
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String street;
    private Integer number;
    private Boolean selected;
    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonIgnore
    private CustomerEntity customer;
    private LocalDate createdAt;
}
