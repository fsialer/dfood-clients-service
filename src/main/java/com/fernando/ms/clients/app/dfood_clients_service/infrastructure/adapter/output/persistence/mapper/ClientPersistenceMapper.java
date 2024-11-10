package com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.mapper;

import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Client;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.models.ClientEntity;

import java.util.List;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientPersistenceMapper {

    List<Client> toClients(List<ClientEntity> clients);
}
