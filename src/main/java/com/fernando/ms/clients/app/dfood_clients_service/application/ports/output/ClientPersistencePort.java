package com.fernando.ms.clients.app.dfood_clients_service.application.ports.output;

import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Client;

import java.util.List;
import java.util.Optional;

public interface ClientPersistencePort {

    List<Client> findAll();
    Optional<Client> findById(Long id);
}
