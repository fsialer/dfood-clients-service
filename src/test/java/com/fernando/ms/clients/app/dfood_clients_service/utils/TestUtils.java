package com.fernando.ms.clients.app.dfood_clients_service.utils;


import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Client;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.request.CreateClientRequest;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.response.ClientResponse;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.models.ClientEntity;

import java.time.LocalDate;

public class TestUtils {

    public static Client buildClientMock(){
        return Client.builder()
                .id(1L)
                .name("Fernando")
                .lastname("Sialer")
                .fullName("Fernando Sialer")
                .phone("965012869")
                .email("asialer05@hotmail.com")
                .userId(1L)
                .build();
    }

    public static ClientEntity buildClientEntityMock(){
        return ClientEntity.builder()
                .id(1L)
                .name("Fernando")
                .lastname("Sialer")
                .fullName("Fernando Sialer")
                .phone("965012869")
                .email("asialer05@hotmail.com")
                .userId(1L)
                .createdAt(LocalDate.now())
                .build();
    }

    public static ClientResponse buildClientResponseMock(){
        return ClientResponse.builder()
                .id(1L)
                .name("Fernando")
                .lastname("Sialer")
                .fullName("Fernando Sialer")
                .phone("965012869")
                .email("asialer05@hotmail.com")
                .build();
    }

    public static CreateClientRequest buildClientCreateRequestMock(){
        return CreateClientRequest.builder()
                .name("Fernando")
                .lastname("Sialer")
                .fullName("Fernando Sialer")
                .phone("965012869")
                .email("asialer05@hotmail.com")
                .userId(1L)
                .build();
    }
}
