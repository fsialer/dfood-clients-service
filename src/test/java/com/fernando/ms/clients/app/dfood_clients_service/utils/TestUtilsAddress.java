package com.fernando.ms.clients.app.dfood_clients_service.utils;

import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Address;
import com.fernando.ms.clients.app.dfood_clients_service.domain.models.enums.StatusCustomer;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.request.CreateAddressRequest;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.response.AddressResponse;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.models.AddressEntity;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.models.CustomerEntity;

import java.time.LocalDate;

public class TestUtilsAddress {
    public static Address buildAddressMock(){
        return Address.builder()
                .id(1L)
                .street("3 de octubre")
                .number(575)
                .selected(true)
                .customerId(1L)
                .build();
    }

    public static Address buildAddressUpdatedMock(){
        return Address.builder()
                .id(1L)
                .street("3 de octubre")
                .number(575)
                .selected(true)
                .customerId(1L)
                .build();
    }

    public static AddressEntity buildAddressEntityMock(){
        return AddressEntity.builder()
                .id(1L)
                .street("3 de octubre")
                .number(575)
                .selected(true)
                .customer(CustomerEntity.builder()
                        .id(1L)
                        .name("Fernando")
                        .lastname("Sialer")
                        .fullName("Fernando Sialer")
                        .phone("965012869")
                        .email("asialer05@hotmail.com")
                        .userId(1L)
                        .createdAt(LocalDate.now())
                        .statusCustomer(StatusCustomer.REGISTERED).build())
                .build();
    }

    public static AddressResponse buildAddressResponseMock(){
        return AddressResponse.builder()
                .id(1L)
                .street("3 de octubre")
                .number(575)
                .selected(true)
                .build();
    }

    public static CreateAddressRequest buildCreateAddressRequest(){
        return CreateAddressRequest.builder()
                .street("3 de octubre")
                .number(575)
                .selected(true)
                .customerId(1L)
                .build();
    }
}
