package com.fernando.ms.clients.app.dfood_clients_service.application.services;

import com.fernando.ms.clients.app.dfood_clients_service.application.ports.input.AddressInputPort;
import com.fernando.ms.clients.app.dfood_clients_service.application.ports.output.AddressPersistencePort;
import com.fernando.ms.clients.app.dfood_clients_service.application.ports.output.ClientPersistencePort;
import com.fernando.ms.clients.app.dfood_clients_service.domain.exceptions.AddressNotFoundException;
import com.fernando.ms.clients.app.dfood_clients_service.domain.exceptions.ClientNotFoundException;
import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService implements AddressInputPort {
    private final AddressPersistencePort addressPersistencePort;
    private final ClientPersistencePort clientPersistencePort;

    @Override
    public List<Address> findAll() {
        return addressPersistencePort.findAll();
    }

    @Override
    public Address findById(Long id) {
        return addressPersistencePort.findById(id).orElseThrow(AddressNotFoundException::new);
    }

    @Override
    public Address save(Address address) {
        return clientPersistencePort.findById(address.getClientId())
                .map(client -> {
                    return addressPersistencePort.save(address,client);
                })
                .orElseThrow(ClientNotFoundException::new);
    }

    @Override
    public Address update(Long id, Address address) {
        return addressPersistencePort.findById(id).map(
                addressUpdated ->{
                    addressUpdated.setNumber(address.getNumber());
                    addressUpdated.setStreet(address.getStreet());
                    addressUpdated.setSelected(address.getSelected());
                    return addressPersistencePort.save(addressUpdated);
                }
        ).orElseThrow(AddressNotFoundException::new);
    }

    @Override
    public void delete(Long id) {
        if(!addressPersistencePort.findById(id).isPresent()){
            throw new AddressNotFoundException();
        }
        addressPersistencePort.delete(id);
    }
}
