package com.fernando.ms.clients.app.dfood_clients_service.application.ports.input;

import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Address;

import java.util.List;

public interface AddressInputPort {
    List<Address> findAll();
}
