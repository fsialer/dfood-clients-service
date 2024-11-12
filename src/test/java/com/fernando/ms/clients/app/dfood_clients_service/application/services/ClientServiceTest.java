package com.fernando.ms.clients.app.dfood_clients_service.application.services;

import com.fernando.ms.clients.app.dfood_clients_service.application.ports.output.ClientPersistencePort;
import com.fernando.ms.clients.app.dfood_clients_service.domain.exceptions.ClientEmailAlreadyExistsException;
import com.fernando.ms.clients.app.dfood_clients_service.domain.exceptions.ClientNotFoundException;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientPersistencePort clientPersistencePort;

    @InjectMocks
    private ClientService clientService;

    @Test
    void shouldReturnClientsWhenListExistData() {
        Client client = TestUtils.buildClientMock();
        when(clientPersistencePort.findAll()).thenReturn(Collections.singletonList(client));
        List<Client> clients = clientService.findAll();
        assertEquals(1, clients.size());
        Mockito.verify(clientPersistencePort, times(1)).findAll();
    }

    @Test
    void shouldReturnListVoidWhenThereDoNotData() {
        when(clientPersistencePort.findAll()).thenReturn(Collections.emptyList());
        List<Client> clients = clientService.findAll();
        assertEquals(0, clients.size());
        Mockito.verify(clientPersistencePort, times(1)).findAll();
    }

    @Test
    void shouldReturnClientWhenFindById() {
        Client client = TestUtils.buildClientMock();
        when(clientPersistencePort.findById(anyLong())).thenReturn(Optional.of(client));
        Client clientRes = clientService.findById(1L);
        assertEquals(client, clientRes);
        assertNotNull(clientRes);
        Mockito.verify(clientPersistencePort, times(1)).findById(anyLong());
    }

    @Test
    void shouldReturnClientNotFoundExceptionWhenFindById() {
        when(clientPersistencePort.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ClientNotFoundException.class, () -> clientService.findById(1L));
        Mockito.verify(clientPersistencePort, times(1)).findById(anyLong());
    }

    @Test
    void shouldReturnClientWhenIntoANewUser() {
        Client client = TestUtils.buildClientMock();
        when(clientPersistencePort.save(any(Client.class))).thenReturn(client);
        when(clientPersistencePort.existsByEmail(anyString())).thenReturn(false);
        Client clientResponse = clientService.save(client);
        assertEquals(client, clientResponse);
        Mockito.verify(clientPersistencePort, times(1)).save(any(Client.class));
        Mockito.verify(clientPersistencePort, times(1)).existsByEmail(anyString());
    }

    @Test
    void shouldReturnClientEmailAlreadyExistsExceptionWhenEmailRepeated() {
        Client client = TestUtils.buildClientMock();
        when(clientPersistencePort.existsByEmail(anyString())).thenReturn(true);
        assertThrows(ClientEmailAlreadyExistsException.class, () -> clientService.save(client));
        Mockito.verify(clientPersistencePort, times(0)).save(any(Client.class));
        Mockito.verify(clientPersistencePort, times(1)).existsByEmail(anyString());
    }

    @Test
    void shouldReturnClientWhenIntoAUserByUpdate() {
        Client client = TestUtils.buildClientMock();
        Client clientEmail = TestUtils.buildClientEmailChangedMock();
        when(clientPersistencePort.findById(anyLong())).thenReturn(Optional.of(client));
        when(clientPersistencePort.existsByEmail(anyString())).thenReturn(false);
        when(clientPersistencePort.save(any(Client.class))).thenReturn(clientEmail);

        Client clientResponse = clientService.update(1L, clientEmail);
        assertEquals(clientEmail, clientResponse);
        Mockito.verify(clientPersistencePort, times(1)).save(any(Client.class));
        Mockito.verify(clientPersistencePort, times(1)).findById(anyLong());
        Mockito.verify(clientPersistencePort, times(1)).existsByEmail(anyString());
    }

    @Test
    void shouldReturnClientNotFoundWhenIntoAUserNotExist() {
        Client client = TestUtils.buildClientMock();
        when(clientPersistencePort.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ClientNotFoundException.class, () -> clientService.update(1L, client));
        Mockito.verify(clientPersistencePort, times(0)).save(any(Client.class));
        Mockito.verify(clientPersistencePort, times(1)).findById(anyLong());
    }

    @Test
    void shouldReturnClientEmailAlreadyExistsExceptionWhenUpdatedEmailRepeated() {
        Client client = TestUtils.buildClientMock();
        Client clientEmail = TestUtils.buildClientEmailChangedMock();
        when(clientPersistencePort.findById(anyLong())).thenReturn(Optional.of(client));
        when(clientPersistencePort.existsByEmail(anyString())).thenReturn(true);
        assertThrows(ClientEmailAlreadyExistsException.class, () -> clientService.update(1L, clientEmail));
        Mockito.verify(clientPersistencePort, times(0)).save(any(Client.class));
        Mockito.verify(clientPersistencePort, times(1)).findById(anyLong());
        Mockito.verify(clientPersistencePort, times(1)).existsByEmail(anyString());
    }

    @Test
    void shouldInactiveAnUserWhenUserFindById(){
        Client clientNew=TestUtils.buildClientInactiveMock();
        when(clientPersistencePort.save(any(Client.class)))
                .thenReturn(clientNew);
        when(clientPersistencePort.findById(anyLong()))
                .thenReturn(Optional.of(clientNew));
        Client client=clientService.inactive(1L);
        assertEquals(clientNew.getStatusClient(),client.getStatusClient());
        Mockito.verify(clientPersistencePort,times(1)).save(any(Client.class));
        Mockito.verify(clientPersistencePort,times(1)).findById(anyLong());

    }

    @Test
    void shouldReturnUserNotFoundExceptionWhenWhenInactiveUserFindById(){
        when(clientPersistencePort.findById(anyLong()))
                .thenReturn(Optional.empty());
        assertThrows(ClientNotFoundException.class,()->clientService.inactive(2L));
        Mockito.verify(clientPersistencePort,times(0)).save(any(Client.class));
        Mockito.verify(clientPersistencePort,times(1)).findById(anyLong());
    }

    @Test
    void shouldReturnVoidWhenAClientDeleteById(){
        Client clientNew=TestUtils.buildClientInactiveMock();
        doNothing().when(clientPersistencePort).delete(anyLong());
        when(clientPersistencePort.findById(anyLong()))
                .thenReturn(Optional.of(clientNew));
        clientService.delete(1L);
        Mockito.verify(clientPersistencePort,times(1)).delete(anyLong());
        Mockito.verify(clientPersistencePort,times(1)).findById(anyLong());
    }
}


