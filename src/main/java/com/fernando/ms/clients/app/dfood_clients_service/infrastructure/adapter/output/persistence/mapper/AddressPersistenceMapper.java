package com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.mapper;

import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Address;
import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Client;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.models.AddressEntity;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.models.ClientEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressPersistenceMapper {

    List<Address> toAddresses(List<AddressEntity> addresses);
}
