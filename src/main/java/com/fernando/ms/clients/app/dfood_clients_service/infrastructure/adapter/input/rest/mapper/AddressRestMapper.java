package com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.mapper;

import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Address;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.request.CreateAddressRequest;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.response.AddressResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressRestMapper {
    List<AddressResponse> toAddressesResponse(List<Address> addresses);
    AddressResponse toAddressResponse(Address address);

    Address toAddress(CreateAddressRequest createAddressRequest);
}
