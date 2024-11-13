package com.fernando.ms.clients.app.dfood_clients_service.infraestructure.output.persistence;

import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Address;
import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Client;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.AddressPersistenceAdapter;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.ClientPersistenceAdapter;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.mapper.AddressPersistenceMapper;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.mapper.ClientPersistenceMapper;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.models.AddressEntity;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.models.ClientEntity;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.repository.AddressJpaRepository;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.repository.ClientJpaRepository;
import com.fernando.ms.clients.app.dfood_clients_service.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddressPersistenceAdapterTest {
    @Mock
    private AddressJpaRepository addressJpaRepository;

    @Mock
    private AddressPersistenceMapper addressPersistenceMapper;

    @InjectMocks
    private AddressPersistenceAdapter addressPersistenceAdapter;

    @Test
    void shouldReturnAListAddressWhenIsRequired(){
        AddressEntity addressEntity= TestUtils.buildAddressEntityMock();
        Address address= TestUtils.buildAddressMock();
        when(addressJpaRepository.findAll()).thenReturn(Collections.singletonList(addressEntity));
        when(addressPersistenceMapper.toAddresses(anyList())).thenReturn(Collections.singletonList(address));

        List<Address> addresses=addressPersistenceAdapter.findAll();
        assertEquals(1,addresses.size());
        Mockito.verify(addressJpaRepository,times(1)).findAll();
        Mockito.verify(addressPersistenceMapper,times(1)).toAddresses(anyList());

    }

    @Test
    void shouldReturnAListVoidAddressWhenThereDoNotData(){

        when(addressJpaRepository.findAll()).thenReturn(Collections.emptyList());
        when(addressPersistenceMapper.toAddresses(anyList())).thenReturn(Collections.emptyList());

        List<Address> addresses=addressPersistenceAdapter.findAll();
        assertEquals(0,addresses.size());
        Mockito.verify(addressJpaRepository,times(1)).findAll();
        Mockito.verify(addressPersistenceMapper,times(1)).toAddresses(anyList());

    }

    @Test
    void shouldReturnAnAddressWhenFindById(){
        AddressEntity addressEntity= TestUtils.buildAddressEntityMock();
        Address address= TestUtils.buildAddressMock();
        when(addressJpaRepository.findById(anyLong())).thenReturn(Optional.of(addressEntity));
        when(addressPersistenceMapper.toAddress(any(AddressEntity.class))).thenReturn(address);
        Optional<Address> addressResponse=addressPersistenceAdapter.findById(1L);
        assertNotNull(addressResponse);
        Mockito.verify(addressJpaRepository,times(1)).findById(anyLong());
        Mockito.verify(addressPersistenceMapper,times(1)).toAddress(any(AddressEntity.class));

    }
}
