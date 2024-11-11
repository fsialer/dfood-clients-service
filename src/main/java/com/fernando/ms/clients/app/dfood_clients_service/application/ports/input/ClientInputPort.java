package com.fernando.ms.clients.app.dfood_clients_service.application.ports.input;

import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Client;

import java.util.List;

public interface ClientInputPort {

    List<Client> findAll();

    Client findById(Long id);

    Client save(Client client);
}