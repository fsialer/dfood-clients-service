package com.fernando.ms.clients.app.dfood_clients_service.application.ports.output;

import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Address;

import java.util.List;
import java.util.Optional;

public interface AddressPersistencePort {
    List<Address> findAll();
    Optional<Address> findById(Long id);
}