package com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.mapper;

import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Address;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.output.persistence.models.AddressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressPersistenceMapper {

    List<Address> toAddresses(List<AddressEntity> addresses);

    Address toAddress(AddressEntity address);

    @Mapping(target = "client", ignore = true)
    AddressEntity toAddressEntity(Address address);


}
