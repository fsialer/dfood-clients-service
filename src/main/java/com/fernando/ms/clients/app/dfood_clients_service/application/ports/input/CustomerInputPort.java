package com.fernando.ms.clients.app.dfood_clients_service.application.ports.input;

import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Customer;

import java.util.List;

public interface CustomerInputPort {

    List<Customer> findAll();

    Customer findById(Long id);

    Customer save(Customer customer);

    Customer update(Long id, Customer customer);

    Customer inactive(Long id);

    void delete(Long id);
}