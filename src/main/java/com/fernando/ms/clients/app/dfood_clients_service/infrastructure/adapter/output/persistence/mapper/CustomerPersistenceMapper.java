package com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.mapper;

import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Customer;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.models.CustomerEntity;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring"
        ,uses = AddressPersistenceMapper.class
        ,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerPersistenceMapper {

    List<Customer> toCustomers(List<CustomerEntity> clients);

    Customer toCustomer(CustomerEntity client);

    @Mapping(target = "addresses", ignore = true)
    CustomerEntity toCustomerEntity(Customer customer);
}
