package com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressResponse {
    private Long id;
    private String street;
    private Integer number;
    private Boolean selected;
    private CustomerResponse customer;
}
