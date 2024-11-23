package com.fernando.ms.clients.app.dfood_clients_service.domain.exceptions;

public class CustomerEmailAlreadyExistsException extends RuntimeException{
    public CustomerEmailAlreadyExistsException(String email){
        super("Client email: " + email + " already exists!");
    }

}
