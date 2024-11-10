package com.fernando.ms.clients.app.dfood_clients_service.application.ports.output;

import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Client;

import java.util.List;

public interface ClientPersistencePort {

    List<Client> findAll();
}
