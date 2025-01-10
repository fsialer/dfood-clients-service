package com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence;

import com.fernando.ms.clients.app.dfood_clients_service.application.ports.output.CustomerPersistencePort;
import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Customer;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.mapper.AddressPersistenceMapper;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.mapper.CustomerPersistenceMapper;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.models.CustomerEntity;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.repository.CustomerJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomerPersistenceAdapter implements CustomerPersistencePort {
    private final CustomerJpaRepository customerJpaRepository;
    private final CustomerPersistenceMapper customerPersistenceMapper;
    private final AddressPersistenceMapper addressPersistenceMapper;
    @Override
    public List<Customer> findAll() {
        return customerPersistenceMapper.toCustomers(customerJpaRepository.findAll());
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerJpaRepository.findById(id).map(customerPersistenceMapper::toCustomer);
    }

    @Override
    public Customer save(Customer customer) {
        CustomerEntity customerEntity = customerPersistenceMapper.toCustomerEntity(customer);
        // Manejar direcciones manualmente si es necesario
        if (customer.getAddresses() != null) {
            customerEntity.setAddresses(customer.getAddresses().stream()
                    .map(addressPersistenceMapper::toAddressEntity)
                    .peek(address -> address.setCustomer(customerEntity)) // Configurar la relación inversa
                    .collect(Collectors.toList()));
        }
        customerEntity.setUserId(customer.getUser().getId());
        return customerPersistenceMapper.toCustomer(customerJpaRepository.save(customerEntity));
    }

    @Override
    public boolean existsByEmail(String email) {
        return customerJpaRepository.existsByEmailIgnoreCase(email);
    }

    @Override
    public void delete(Long id) {
        customerJpaRepository.deleteById(id);
    }

    @Override
    public void verifyExistsById(Long id) {
        customerJpaRepository.existsById(id);
    }


}
