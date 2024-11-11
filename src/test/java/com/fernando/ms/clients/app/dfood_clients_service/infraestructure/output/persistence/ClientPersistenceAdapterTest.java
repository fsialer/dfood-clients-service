package com.fernando.ms.clients.app.dfood_clients_service.infraestructure.output.persistence;

import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Client;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.ClientPersistenceAdapter;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.mapper.ClientPersistenceMapper;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.models.ClientEntity;
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
public class ClientPersistenceAdapterTest {

    @Mock
    private ClientJpaRepository clientJpaRepository;

    @Mock
    private ClientPersistenceMapper clientPersistenceMapper;

    @InjectMocks
    private ClientPersistenceAdapter clientPersistenceAdapter;

    @Test
    void shouldReturnAListClientWhenIsRequired(){
        ClientEntity clientEntity= TestUtils.buildClientEntityMock();
        Client client=TestUtils.buildClientMock();
        when(clientJpaRepository.findAll()).thenReturn(Collections.singletonList(clientEntity));
        when(clientPersistenceMapper.toClients(anyList())).thenReturn(Collections.singletonList(client));

        List<Client> clients=clientPersistenceAdapter.findAll();
        assertEquals(1,clients.size());
        Mockito.verify(clientJpaRepository,times(1)).findAll();
        Mockito.verify(clientPersistenceMapper,times(1)).toClients(anyList());

    }

    @Test
    void shouldReturnAListVoidClientWhenThereDoNotData(){

        when(clientJpaRepository.findAll()).thenReturn(Collections.emptyList());
        when(clientPersistenceMapper.toClients(anyList())).thenReturn(Collections.emptyList());

        List<Client> clients=clientPersistenceAdapter.findAll();
        assertEquals(0,clients.size());
        Mockito.verify(clientJpaRepository,times(1)).findAll();
        Mockito.verify(clientPersistenceMapper,times(1)).toClients(anyList());

    }

    @Test
    void shouldReturnAClientWhenFindById(){
        ClientEntity clientEntity= TestUtils.buildClientEntityMock();
        Client client= TestUtils.buildClientMock();
        when(clientJpaRepository.findById(anyLong())).thenReturn(Optional.of(clientEntity));
        when(clientPersistenceMapper.toClient(any(ClientEntity.class))).thenReturn(client);
        Optional<Client> clientResponse=clientPersistenceAdapter.findById(1L);
        assertNotNull(clientResponse);
        Mockito.verify(clientJpaRepository,times(1)).findById(anyLong());
        Mockito.verify(clientPersistenceMapper,times(1)).toClient(any(ClientEntity.class));

    }

    @Test
    void shouldSaveAnUserWhenIntoANewClient(){
        ClientEntity clientEntity= TestUtils.buildClientEntityMock();
        Client client= TestUtils.buildClientMock();
        when(clientJpaRepository.save(any(ClientEntity.class))).thenReturn(clientEntity);
        when(clientPersistenceMapper.toClientEntity(any(Client.class))).thenReturn(clientEntity);
        when(clientPersistenceMapper.toClient(any(ClientEntity.class))).thenReturn(client);
        Client clientNew=clientPersistenceAdapter.save(client);
        assertNotNull(clientNew);
        assertEquals(client,clientNew);
        Mockito.verify(clientJpaRepository,times(1)).save(any(ClientEntity.class));
        Mockito.verify(clientPersistenceMapper,times(1)).toClientEntity(any(Client.class));
        Mockito.verify(clientPersistenceMapper,times(1)).toClient(any(ClientEntity.class));
    }
}
