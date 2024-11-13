package com.fernando.ms.clients.app.dfood_clients_service.utils;


import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Address;
import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Client;
import com.fernando.ms.clients.app.dfood_clients_service.domain.models.enums.StatusClient;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.request.CreateClientRequest;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.response.AddressResponse;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.response.ClientResponse;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.models.AddressEntity;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.models.ClientEntity;

import java.time.LocalDate;

public class TestUtils {

    public static Client buildClientMock() {
        return Client.builder()
                .id(1L)
                .name("Fernando")
                .lastname("Sialer")
                .fullName("Fernando Sialer")
                .phone("965012869")
                .email("asialer05@hotmail.com")
                .userId(1L)
                .statusClient(StatusClient.REGISTERED)
                .build();
    }

    public static Client buildClientInactiveMock() {
        return Client.builder()
                .id(1L)
                .name("Fernando")
                .lastname("Sialer")
                .fullName("Fernando Sialer")
                .phone("965012869")
                .email("asialer05@hotmail.com")
                .userId(1L)
                .statusClient(StatusClient.INACTIVE)
                .build();
    }


    public static Client buildClientEmailChangedMock() {
        return Client.builder()
                .id(1L)
                .name("Fernando")
                .lastname("Sialer")
                .fullName("Fernando Sialer")
                .phone("965012869")
                .email("asialer02@hotmail.com")
                .userId(1L)
                .statusClient(StatusClient.REGISTERED)
                .build();
    }

    public static CreateClientRequest buildClientCreateRequestMock() {
        return CreateClientRequest.builder()
                .name("Fernando")
                .lastname("Sialer")
                .phone("965012869")
                .email("asialer02@hotmail.com")
                .userId(1L)
                .build();
    }

    public static ClientResponse buildClientResponseMock() {
        return ClientResponse.builder()
                .id(1L)
                .name("Fernando")
                .lastname("Sialer")
                .fullName("Fernando Sialer")
                .phone("965012869")
                .email("asialer02@hotmail.com")
                .statusClient(StatusClient.REGISTERED)
                .build();
    }




    public static ClientEntity buildClientEntityMock() {
        return ClientEntity.builder()
                .id(1L)
                .name("Fernando")
                .lastname("Sialer")
                .fullName("Fernando Sialer")
                .phone("965012869")
                .email("asialer05@hotmail.com")
                .userId(1L)
                .createdAt(LocalDate.now())
                .statusClient(StatusClient.REGISTERED)
                .build();
    }

    public static ClientResponse buildClientInactiveResponseMock() {
        return ClientResponse.builder()
                .id(1L)
                .name("Fernando")
                .lastname("Sialer")
                .fullName("Fernando Sialer")
                .phone("965012869")
                .email("asialer02@hotmail.com")
                .statusClient(StatusClient.INACTIVE)
                .build();
    }

    public static Address buildAddressMock(){
        return Address.builder()
                .id(1L)
                .street("3 de octubre")
                .number(575)
                .selected(true)
                .client(Client.builder()
                        .id(1L)
                        .name("Fernando")
                        .lastname("Sialer")
                        .fullName("Fernando Sialer")
                        .phone("965012869")
                        .email("asialer05@hotmail.com")
                        .userId(1L)
                        .statusClient(StatusClient.REGISTERED).build())
                .build();
    }

    public static AddressEntity buildAddressEntityMock(){
        return AddressEntity.builder()
                .id(1L)
                .street("3 de octubre")
                .number(575)
                .selected(true)
                .client(ClientEntity.builder()
                        .id(1L)
                        .name("Fernando")
                        .lastname("Sialer")
                        .fullName("Fernando Sialer")
                        .phone("965012869")
                        .email("asialer05@hotmail.com")
                        .userId(1L)
                        .createdAt(LocalDate.now())
                        .statusClient(StatusClient.REGISTERED).build())
                .build();
    }

    public static AddressResponse buildAddressResponseMock(){
        return AddressResponse.builder()
                .id(1L)
                .street("3 de octubre")
                .number(575)
                .selected(true)
                .client(ClientResponse.builder()
                        .id(1L)
                        .name("Fernando")
                        .lastname("Sialer")
                        .fullName("Fernando Sialer")
                        .phone("965012869")
                        .email("asialer05@hotmail.com")
                        .statusClient(StatusClient.REGISTERED).build())
                .build();
    }







}