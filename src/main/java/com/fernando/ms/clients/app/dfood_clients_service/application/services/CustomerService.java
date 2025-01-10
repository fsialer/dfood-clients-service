package com.fernando.ms.clients.app.dfood_clients_service.application.services;

import com.fernando.ms.clients.app.dfood_clients_service.application.ports.input.CustomerInputPort;
import com.fernando.ms.clients.app.dfood_clients_service.application.ports.output.CustomerPersistencePort;
import com.fernando.ms.clients.app.dfood_clients_service.domain.exceptions.CustomerEmailAlreadyExistsException;
import com.fernando.ms.clients.app.dfood_clients_service.domain.exceptions.CustomerNotFoundException;
import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Customer;
import com.fernando.ms.clients.app.dfood_clients_service.domain.models.enums.StatusCustomer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService implements CustomerInputPort {

    private final CustomerPersistencePort customerPersistencePort;

    @Override
    public List<Customer> findAll() {
        return customerPersistencePort.findAll();
    }

    @Override
    public Customer findById(Long id) {
        return customerPersistencePort.findById(id).orElseThrow(CustomerNotFoundException::new);
    }

    @Override
    public Customer save(Customer customer) {
        if(customerPersistencePort.existsByEmail(customer.getEmail())){
            throw new CustomerEmailAlreadyExistsException(customer.getEmail());
        }
        return customerPersistencePort.save(customer);
    }

    @Override
    public Customer update(Long id, Customer customer) {
        return customerPersistencePort.findById(id)
                .map(clientUpdated -> {
                    clientUpdated.setName(customer.getName());
                    clientUpdated.setLastname(customer.getLastname());
                    clientUpdated.setPhone(customer.getPhone());
                    System.out.println(clientUpdated.getEmail().equals(customer.getEmail()));
                    if (!clientUpdated.getEmail().equals(customer.getEmail())) {
                        if (customerPersistencePort.existsByEmail(customer.getEmail())) {
                            throw new CustomerEmailAlreadyExistsException(customer.getEmail());
                        }
                        clientUpdated.setEmail(customer.getEmail());
                    }
                    return customerPersistencePort.save(clientUpdated);
                })
                .orElseThrow(CustomerNotFoundException::new);
    }

    @Override
    public Customer inactive(Long id) {
        return customerPersistencePort.findById(id)
                .map(clientUpdated->{
                    clientUpdated.setStatusCustomer(StatusCustomer.INACTIVE);
                    return customerPersistencePort.save(clientUpdated);
                })
                .orElseThrow(CustomerNotFoundException::new);
    }

    @Override
    public void delete(Long id) {
        if(!customerPersistencePort.findById(id).isPresent()){
            throw new CustomerNotFoundException();
        }
        customerPersistencePort.delete(id);
    }

    @Override
    public void verifyExistsById(Long id) {
        if (!customerPersistencePort.findById(id).isPresent()){
            throw new CustomerNotFoundException();
        }

        customerPersistencePort.verifyExistsById(id);
    }

}
