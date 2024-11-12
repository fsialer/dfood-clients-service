package com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.response;

import com.fernando.ms.clients.app.dfood_clients_service.domain.models.enums.StatusClient;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientResponse {
    private Long id;
    private String name;
    private String lastname;
    private String fullName;
    private String phone;
    private String email;
    private StatusClient statusClient;
}
