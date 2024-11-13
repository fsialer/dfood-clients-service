package com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest;

import com.fernando.ms.clients.app.dfood_clients_service.application.ports.input.AddressInputPort;
import com.fernando.ms.clients.app.dfood_clients_service.application.ports.input.ClientInputPort;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.mapper.AddressRestMapper;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.mapper.ClientRestMapper;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.response.AddressResponse;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.response.ClientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
public class AddressRestAdapter {
    private final AddressInputPort addressInputPort;
    private final AddressRestMapper addressRestMapper;

    @GetMapping
    public ResponseEntity<List<AddressResponse>> findAll(){
        return ResponseEntity.ok().body(addressRestMapper.toAddressesResponse(addressInputPort.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(addressRestMapper.toAddressResponse(addressInputPort.findById(id)));
    }
}
