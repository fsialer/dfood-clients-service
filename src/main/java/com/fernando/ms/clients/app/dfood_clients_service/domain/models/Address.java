package com.fernando.ms.clients.app.dfood_clients_service.domain.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
    private Long id;
    private String street;
    private Integer number;
    private Boolean selected;
    private Long customerId;
}
