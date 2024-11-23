package com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest;

import com.fernando.ms.clients.app.dfood_clients_service.application.ports.input.AddressInputPort;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.mapper.AddressRestMapper;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.request.CreateAddressRequest;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.response.AddressResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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

    @PostMapping
    public ResponseEntity<AddressResponse> save(@Valid @RequestBody CreateAddressRequest rq){
        AddressResponse clientResponse=addressRestMapper.toAddressResponse(addressInputPort.save(addressRestMapper.toAddress(rq)));
        return ResponseEntity.created(URI.create("/clients/".concat(clientResponse.getId().toString()))).body(clientResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponse> update(@PathVariable Long id,@Valid @RequestBody CreateAddressRequest rq){
        return ResponseEntity.ok(addressRestMapper.toAddressResponse(addressInputPort.update(id,addressRestMapper.toAddress(rq))));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        addressInputPort.delete(id);
    }
}
