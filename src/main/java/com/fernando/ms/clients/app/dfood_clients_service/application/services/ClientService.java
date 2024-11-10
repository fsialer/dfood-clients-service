package com.fernando.ms.clients.app.dfood_clients_service.application.services;

import com.fernando.ms.clients.app.dfood_clients_service.application.ports.input.ClientInputPort;
import com.fernando.ms.clients.app.dfood_clients_service.application.ports.output.ClientPersistencePort;
import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService implements ClientInputPort {

    private final ClientPersistencePort clientPersistencePort;

    @Override
    public List<Client> findAll() {
        return clientPersistencePort.findAll();
    }
}
