package com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.mapper;

import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Customer;
import com.fernando.ms.clients.app.dfood_clients_service.domain.models.enums.StatusCustomer;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.request.CreateCustomerRequest;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.response.CustomerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",uses = AddressRestMapper.class)
public interface CustomerRestMapper {

    List<CustomerResponse> toCustomersResponse(List<Customer> customers);


    CustomerResponse toCustomerResponse(Customer customer);

    @Mapping(target = "fullName", expression = "java(mapFullName(client))")
    @Mapping(target = "statusCustomer", expression = "java(mapStatusCustomer())")
    @Mapping(target = "addresses", source = "addresses")
    Customer toCustomer (CreateCustomerRequest client);

    default String mapFullName(CreateCustomerRequest customer){
        return customer.getName()+" "+customer.getLastname();
    }

    default StatusCustomer mapStatusCustomer(){
        return StatusCustomer.REGISTERED;
    }

}
