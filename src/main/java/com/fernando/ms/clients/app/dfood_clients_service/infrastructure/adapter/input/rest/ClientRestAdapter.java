package com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest;

import com.fernando.ms.clients.app.dfood_clients_service.application.ports.input.ClientInputPort;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.mapper.ClientRestMapper;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.request.CreateClientRequest;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.response.ClientResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientRestAdapter {
    private final ClientInputPort clientInputPort;
    private final ClientRestMapper clientRestMapper;

    @GetMapping
    public ResponseEntity<List<ClientResponse>> findAll(){
        return ResponseEntity.ok().body(clientRestMapper.toClientsResponse(clientInputPort.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(clientRestMapper.toClientResponse(clientInputPort.findById(id)));
    }

    @PostMapping
    public ResponseEntity<ClientResponse> save(@Valid @RequestBody CreateClientRequest rq){
        ClientResponse clientResponse=clientRestMapper.toClientResponse(clientInputPort.save(clientRestMapper.toClient(rq)));
        return ResponseEntity.created(URI.create("/clients/".concat(clientResponse.getId().toString()))).body(clientResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> update(@PathVariable Long id,@Valid @RequestBody CreateClientRequest rq){
        return ResponseEntity.ok(clientRestMapper.toClientResponse(clientInputPort.update(id,clientRestMapper.toClient(rq))));
    }

    @PutMapping("/{id}/inactive")
    public ResponseEntity<ClientResponse> inactive(@PathVariable Long id){
        return ResponseEntity.ok(clientRestMapper.toClientResponse(clientInputPort.inactive(id)));
    }

}
