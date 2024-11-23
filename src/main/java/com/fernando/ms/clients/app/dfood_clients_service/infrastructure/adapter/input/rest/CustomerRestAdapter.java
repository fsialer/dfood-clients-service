package com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest;

import com.fernando.ms.clients.app.dfood_clients_service.application.ports.input.CustomerInputPort;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.mapper.CustomerRestMapper;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.request.CreateCustomerRequest;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.response.CustomerResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerRestAdapter {
    private final CustomerInputPort customerInputPort;
    private final CustomerRestMapper customerRestMapper;

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> findAll(){
        return ResponseEntity.ok().body(customerRestMapper.toCustomersResponse(customerInputPort.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(customerRestMapper.toCustomerResponse(customerInputPort.findById(id)));
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> save(@Valid @RequestBody CreateCustomerRequest rq){
        CustomerResponse customerResponse = customerRestMapper.toCustomerResponse(customerInputPort.save(customerRestMapper.toCustomer(rq)));
        return ResponseEntity.created(URI.create("/clients/".concat(customerResponse.getId().toString()))).body(customerResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> update(@PathVariable Long id, @Valid @RequestBody CreateCustomerRequest rq){
        return ResponseEntity.ok(customerRestMapper.toCustomerResponse(customerInputPort.update(id, customerRestMapper.toCustomer(rq))));
    }

    @PutMapping("/{id}/inactive")
    public ResponseEntity<CustomerResponse> inactive(@PathVariable Long id){
        return ResponseEntity.ok(customerRestMapper.toCustomerResponse(customerInputPort.inactive(id)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        customerInputPort.delete(id);
    }

}
