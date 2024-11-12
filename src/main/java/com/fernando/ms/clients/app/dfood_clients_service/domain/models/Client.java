package com.fernando.ms.clients.app.dfood_clients_service.domain.models;

import com.fernando.ms.clients.app.dfood_clients_service.domain.models.enums.StatusClient;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Client {
    private Long id;
    private String name;
    private String lastname;
    private String fullName;
    private String phone;
    private String email;
    private Long userId;
    private StatusClient statusClient;
}
