package com.fernando.ms.clients.app.dfood_clients_service.application.services;

import com.fernando.ms.clients.app.dfood_clients_service.application.ports.output.ClientPersistencePort;
import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Client;
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
public class ClientServiceTest {

    @Mock
    private ClientPersistencePort clientPersistencePort;

    @InjectMocks
    private ClientService clientService;

    @Test
    void shouldReturnClientsWhenListExistData(){
        Client client= TestUtils.buildClientMock();
        when(clientPersistencePort.findAll()).thenReturn(Collections.singletonList(client));
        List<Client> clients=clientService.findAll();
        assertEquals(1,clients.size());
        Mockito.verify(clientPersistencePort,times(1)).findAll();
    }

    @Test
    void shouldReturnListVoidWhenThereDoNotData(){
        when(clientPersistencePort.findAll()).thenReturn(Collections.emptyList());
        List<Client> clients=clientService.findAll();
        assertEquals(0,clients.size());
        Mockito.verify(clientPersistencePort,times(1)).findAll();
    }
}
