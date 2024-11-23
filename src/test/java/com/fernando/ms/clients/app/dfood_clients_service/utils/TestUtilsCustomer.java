package com.fernando.ms.clients.app.dfood_clients_service.utils;


import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Address;
import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Customer;
import com.fernando.ms.clients.app.dfood_clients_service.domain.models.enums.StatusCustomer;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.request.CreateAddressRequest;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.request.CreateCustomerRequest;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.response.CustomerResponse;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.models.AddressEntity;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.models.CustomerEntity;

import java.time.LocalDate;
import java.util.List;

public class TestUtilsCustomer {

    public static Customer buildCustomerMock() {
        return Customer.builder()
                .id(1L)
                .name("Fernando")
                .lastname("Sialer")
                .fullName("Fernando Sialer")
                .phone("965012869")
                .email("asialer05@hotmail.com")
                .userId(1L)
                .statusCustomer(StatusCustomer.REGISTERED)
                .build();
    }

    public static Customer buildCustomerAddressMock() {
        return Customer.builder()
                .id(1L)
                .name("Fernando")
                .lastname("Sialer")
                .fullName("Fernando Sialer")
                .phone("965012869")
                .email("asialer05@hotmail.com")
                .userId(1L)
                .statusCustomer(StatusCustomer.REGISTERED)
                .addresses(List.of(
                        Address.builder()
                                .street("3 de octubre")
                                .number(575)
                                .selected(true)
                                .build()))
                .build();
    }

    public static Customer buildCustomerInactiveMock() {
        return Customer.builder()
                .id(1L)
                .name("Fernando")
                .lastname("Sialer")
                .fullName("Fernando Sialer")
                .phone("965012869")
                .email("asialer05@hotmail.com")
                .userId(1L)
                .statusCustomer(StatusCustomer.INACTIVE)
                .build();
    }


    public static Customer buildCustomerEmailChangedMock() {
        return Customer.builder()
                .id(1L)
                .name("Fernando")
                .lastname("Sialer")
                .fullName("Fernando Sialer")
                .phone("965012869")
                .email("asialer02@hotmail.com")
                .userId(1L)
                .statusCustomer(StatusCustomer.REGISTERED)
                .build();
    }

    public static CreateCustomerRequest buildCustomerCreateRequestMock() {
        return CreateCustomerRequest.builder()
                .name("Fernando")
                .lastname("Sialer")
                .phone("965012869")
                .email("asialer02@hotmail.com")
                .userId(1L)
                .build();
    }

    public static CreateCustomerRequest buildCustomerAddressCreateRequestMock() {
        return CreateCustomerRequest.builder()
                .name("Fernando")
                .lastname("Sialer")
                .phone("965012869")
                .email("asialer02@hotmail.com")
                .userId(1L)
                .addresses(
                        List.of(
                                CreateAddressRequest
                                        .builder()
                                        .street("3 de octubre")
                                        .number(575)
                                        .selected(true)
                                        .build()
                        )
                )
                .build();
    }

    public static CustomerResponse buildCustomerResponseMock() {
        return CustomerResponse.builder()
                .id(1L)
                .name("Fernando")
                .lastname("Sialer")
                .fullName("Fernando Sialer")
                .phone("965012869")
                .email("asialer02@hotmail.com")
                .statusCustomer(StatusCustomer.REGISTERED)
                .build();
    }




    public static CustomerEntity buildCustomerEntityMock() {
        return CustomerEntity.builder()
                .id(1L)
                .name("Fernando")
                .lastname("Sialer")
                .fullName("Fernando Sialer")
                .phone("965012869")
                .email("asialer05@hotmail.com")
                .userId(1L)
                .createdAt(LocalDate.now())
                .statusCustomer(StatusCustomer.REGISTERED)
                .build();
    }

    public static CustomerEntity buildCustomerAddressesEntityMock() {
        return CustomerEntity.builder()
                .id(1L)
                .name("Fernando")
                .lastname("Sialer")
                .fullName("Fernando Sialer")
                .phone("965012869")
                .email("asialer05@hotmail.com")
                .userId(1L)
                .createdAt(LocalDate.now())
                .statusCustomer(StatusCustomer.REGISTERED)
                .addresses(List.of(
                        AddressEntity
                                .builder()
                                .street("3 de octubre")
                                .number(575)
                                .selected(true)
                                .build()
                ))
                .build();
    }

    public static CustomerResponse buildCustomerInactiveResponseMock() {
        return CustomerResponse.builder()
                .id(1L)
                .name("Fernando")
                .lastname("Sialer")
                .fullName("Fernando Sialer")
                .phone("965012869")
                .email("asialer02@hotmail.com")
                .statusCustomer(StatusCustomer.INACTIVE)
                .build();
    }












}