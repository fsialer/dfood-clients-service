package com.fernando.ms.clients.app.dfood_clients_service.application.ports.input;

import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Address;

import java.util.List;

public interface AddressInputPort {
    List<Address> findAll();
    Address findById(Long id);
    Address save(Address address);
    Address update(Long id,Address Address);
    void delete(Long id);
}
   