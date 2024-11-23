package com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.response;

import com.fernando.ms.clients.app.dfood_clients_service.domain.models.enums.StatusCustomer;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponse {
    private Long id;
    private String name;
    private String lastname;
    private String fullName;
    private String phone;
    private String email;
    private StatusCustomer statusCustomer;
    private List<AddressResponse> addresses;
}
