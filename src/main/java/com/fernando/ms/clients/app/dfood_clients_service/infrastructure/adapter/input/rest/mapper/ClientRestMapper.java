package com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.mapper;

import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Client;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.request.CreateClientRequest;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.response.ClientResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientRestMapper {

    List<ClientResponse> toClientsResponse(List<Client> clients);

    @Mapping(target = "fullName", expression = "java(mapFullName(client))")
    ClientResponse toClientResponse(Client client);

    Client toClient (CreateClientRequest client);

    default String mapFullName(Client client){
        return client.getName()+" "+client.getLastname();
    }

}
