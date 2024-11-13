package com.fernando.ms.clients.app.dfood_clients_service.application.services;

import com.fernando.ms.clients.app.dfood_clients_service.application.ports.input.AddressInputPort;
import com.fernando.ms.clients.app.dfood_clients_service.application.ports.output.AddressPersistencePort;
import com.fernando.ms.clients.app.dfood_clients_service.domain.exceptions.AddressNotFoundException;
import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService implements AddressInputPort {
    private final AddressPersistencePort addressPersistencePort;

    @Override
    public List<Address> findAll() {
        return addressPersistencePort.findAll();
    }

    @Override
    public Address findById(Long id) {
        return addressPersistencePort.findById(id).orElseThrow(AddressNotFoundException::new);
    }
}
