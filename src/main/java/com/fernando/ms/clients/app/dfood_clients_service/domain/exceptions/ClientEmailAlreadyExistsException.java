package com.fernando.ms.clients.app.dfood_clients_service.domain.exceptions;

public class ClientEmailAlreadyExistsException extends RuntimeException{
    public ClientEmailAlreadyExistsException(String email){
        super("Client email: " + email + " already exists!");
    }

}
