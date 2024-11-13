package com.fernando.ms.clients.app.dfood_clients_service.application.services;


import com.fernando.ms.clients.app.dfood_clients_service.application.ports.output.AddressPersistencePort;
import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Address;
import com.fernando.ms.clients.app.dfood_clients_service.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {
    @Mock
    private AddressPersistencePort addressPersistencePort;

    @InjectMocks
    private AddressService addressService;

    @Test
    void shouldReturnAddressWhenListExistData() {
        Address address = TestUtils.buildAddressMock();
        when(addressPersistencePort.findAll()).thenReturn(Collections.singletonList(address));
        List<Address> addresses = addressService.findAll();
        assertEquals(1, addresses.size());
        Mockito.verify(addressPersistencePort, times(1)).findAll();
    }

    @Test
    void shouldReturnListVoidWhenThereDoNotData() {
        when(addressPersistencePort.findAll()).thenReturn(Collections.emptyList());
        List<Address> addresses = addressService.findAll();
        assertEquals(0, addresses.size());
        Mockito.verify(addressPersistencePort, times(1)).findAll();
    }
}
