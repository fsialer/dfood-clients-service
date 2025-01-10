package com.fernando.ms.clients.app.dfood_clients_service.application.ports.output;

import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerPersistencePort {

    List<Customer> findAll();
    Optional<Customer> findById(Long id);
    Customer save(Customer customer);
    boolean existsByEmail(String email);
    void delete(Long id);
    void verifyExistsById(Long id);
}
