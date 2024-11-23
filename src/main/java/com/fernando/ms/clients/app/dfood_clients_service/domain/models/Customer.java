package com.fernando.ms.clients.app.dfood_clients_service.domain.models;

import com.fernando.ms.clients.app.dfood_clients_service.domain.models.enums.StatusCustomer;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {
    private Long id;
    private String name;
    private String lastname;
    private String fullName;
    private String phone;
    private String email;
    private Long userId;
    private StatusCustomer statusCustomer;
    private List<Address> addresses;
}
