package com.fernando.ms.clients.app.dfood_clients_service.application.services;

import com.fernando.ms.clients.app.dfood_clients_service.application.ports.input.ClientInputPort;
import com.fernando.ms.clients.app.dfood_clients_service.application.ports.output.ClientPersistencePort;
import com.fernando.ms.clients.app.dfood_clients_service.domain.exceptions.ClientEmailAlreadyExistsException;
import com.fernando.ms.clients.app.dfood_clients_service.domain.exceptions.ClientNotFoundException;
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

    @Override
    public Client findById(Long id) {
        return clientPersistencePort.findById(id).orElseThrow(ClientNotFoundException::new);
    }

    @Override
    public Client save(Client client) {
        if(clientPersistencePort.existsByEmail(client.getEmail())){
            throw new ClientEmailAlreadyExistsException(client.getEmail());
        }
        return clientPersistencePort.save(client);
    }

    @Override
    public Client update(Long id, Client client) {
        return clientPersistencePort.findById(id)
                .map(clientUpdated->{
                    clientUpdated.setName(client.getName());
                    clientUpdated.setLastname(client.getLastname());
                    clientUpdated.setPhone(client.getPhone());
                    System.out.println(clientUpdated.getEmail().equals(client.getEmail()));
                    if(!clientUpdated.getEmail().equals(client.getEmail())){

                        if(clientPersistencePort.existsByEmail(client.getEmail())){
                            throw new ClientEmailAlreadyExistsException(client.getEmail());
                        }
                        clientUpdated.setEmail(client.getEmail());
                    }
                    return clientPersistencePort.save(clientUpdated);
                })
                .orElseThrow(ClientNotFoundException::new);
    }


}
