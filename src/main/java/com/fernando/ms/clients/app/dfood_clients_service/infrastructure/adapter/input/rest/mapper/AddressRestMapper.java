package com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.mapper;

import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Address;
import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Client;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.response.AddressResponse;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.response.ClientResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressRestMapper {
    List<AddressResponse> toAddressesResponse(List<Address> addresses);
}
