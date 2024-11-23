package com.fernando.ms.clients.app.dfood_clients_service.application.services;

import com.fernando.ms.clients.app.dfood_clients_service.application.ports.input.AddressInputPort;
import com.fernando.ms.clients.app.dfood_clients_service.application.ports.output.AddressPersistencePort;
import com.fernando.ms.clients.app.dfood_clients_service.application.ports.output.CustomerPersistencePort;
import com.fernando.ms.clients.app.dfood_clients_service.domain.exceptions.AddressNotFoundException;
import com.fernando.ms.clients.app.dfood_clients_service.domain.exceptions.CustomerNotFoundException;
import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService implements AddressInputPort {
    private final AddressPersistencePort addressPersistencePort;
    private final CustomerPersistencePort customerPersistencePort;

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
        return customerPersistencePort.findById(address.getCustomerId())
                .map(client -> {
                    return addressPersistencePort.save(address,client);
                })
                .orElseThrow(CustomerNotFoundException::new);
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
