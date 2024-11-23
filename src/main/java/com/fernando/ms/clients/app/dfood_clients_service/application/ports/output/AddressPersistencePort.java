package com.fernando.ms.clients.app.dfood_clients_service.application.ports.output;

import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Address;
import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Customer;

import java.util.List;
import java.util.Optional;

public interface AddressPersistencePort {
    List<Address> findAll();
    Optional<Address> findById(Long id);
    Address save(Address address);
    Address save(Address address, Customer customer);
    void delete(Long id);
}