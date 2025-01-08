package com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence;

import com.fernando.ms.clients.app.dfood_clients_service.application.ports.output.AddressPersistencePort;
import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Address;
import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Customer;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.mapper.AddressPersistenceMapper;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.mapper.CustomerPersistenceMapper;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.models.AddressEntity;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.models.CustomerEntity;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.repository.AddressJpaRepository;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.repository.CustomerUserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AddressPersistenceAdapter implements AddressPersistencePort {

    private final AddressJpaRepository addressJpaRepository;
    private final AddressPersistenceMapper addressPersistenceMapper;
    private final CustomerPersistenceMapper customerPersistenceMapper;


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
    public Address save(Address address, Customer customer) {
        AddressEntity addressEntity=addressPersistenceMapper.toAddressEntity(address);
        CustomerEntity customerEntity = customerPersistenceMapper.toCustomerEntity(customer);
        addressEntity.setCustomer(customerEntity);
        return addressPersistenceMapper.toAddress(addressJpaRepository.save(addressEntity));
    }

    @Override
    public void delete(Long id) {
        addressJpaRepository.deleteById(id);
    }
}
