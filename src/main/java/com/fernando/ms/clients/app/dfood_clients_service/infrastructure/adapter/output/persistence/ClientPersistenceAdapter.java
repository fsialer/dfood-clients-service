package com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence;

import com.fernando.ms.clients.app.dfood_clients_service.application.ports.output.ClientPersistencePort;
import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Client;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.mapper.ClientPersistenceMapper;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.repository.ClientJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClientPersistenceAdapter implements ClientPersistencePort {
    private final ClientJpaRepository clientJpaRepository;
    private final ClientPersistenceMapper clientPersistenceMapper;
    @Override
    public List<Client> findAll() {
        return clientPersistenceMapper.toClients(clientJpaRepository.findAll());
    }

    @Override
    public Optional<Client> findById(Long id) {
        return clientJpaRepository.findById(id).map(clientPersistenceMapper::toClient);
    }

    @Override
    public Client save(Client client) {
        return clientPersistenceMapper.toClient(clientJpaRepository.save(clientPersistenceMapper.toClientEntity(client)));
    }

    @Override
    public boolean existsByEmail(String email) {
        return clientJpaRepository.existsByEmailIgnoreCase(email);
    }


}
