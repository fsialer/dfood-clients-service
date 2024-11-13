package com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence;

import com.fernando.ms.clients.app.dfood_clients_service.application.ports.output.AddressPersistencePort;
import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Address;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.mapper.AddressPersistenceMapper;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.models.AddressEntity;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.repository.AddressJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class AddressPersistenceAdapter implements AddressPersistencePort {

    private final AddressJpaRepository addressJpaRepository;
    private final AddressPersistenceMapper addressPersistenceMapper;


    @Override
    public List<Address> findAll() {
        return addressPersistenceMapper.toAddresses(addressJpaRepository.findAll());
    }
}
