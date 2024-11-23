package com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence;

import com.fernando.ms.clients.app.dfood_clients_service.application.ports.output.AddressPersistencePort;
import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Address;
import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Client;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.mapper.AddressPersistenceMapper;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.mapper.ClientPersistenceMapper;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.models.AddressEntity;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.models.ClientEntity;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.repository.AddressJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AddressPersistenceAdapter implements AddressPersistencePort {

    private final AddressJpaRepository addressJpaRepository;
    private final AddressPersistenceMapper addressPersistenceMapper;
    private final ClientPersistenceMapper clientPersistenceMapper;


    @Override
    public List<Address> findAll() {
        return addressPersistenceMapper.toAddresses(addressJpaRepository.findAll());
    }

    @Override
    public Optional<Address> findById(Long id) {
        return addressJpaRepository.findById(id).map(addressPersistenceMapper::toAddress);
    }

    @Override
    public Address save(Address address) {
        return addressPersistenceMapper.toAddress(addressJpaRepository.save(addressPersistenceMapper.toAddressEntity(address)));
    }

    @Override
    public Address save(Address address, Client client) {
        AddressEntity addressEntity=addressPersistenceMapper.toAddressEntity(address);
        ClientEntity clientEntity=clientPersistenceMapper.toClientEntity(client);
        addressEntity.setClient(clientEntity);
        return addressPersistenceMapper.toAddress(addressJpaRepository.save(addressEntity));
    }

    @Override
    public void delete(Long id) {
        addressJpaRepository.deleteById(id);
    }
}
