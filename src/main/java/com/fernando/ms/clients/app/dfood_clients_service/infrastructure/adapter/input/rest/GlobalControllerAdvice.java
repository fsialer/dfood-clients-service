package com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest;

import com.fernando.ms.clients.app.dfood_clients_service.domain.exceptions.AddressNotFoundException;
import com.fernando.ms.clients.app.dfood_clients_service.domain.exceptions.CustomerEmailAlreadyExistsException;
import com.fernando.ms.clients.app.dfood_clients_service.domain.exceptions.CustomerNotFoundException;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.util.Collections;

import static com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.enums.ErrorType.FUNCTIONAL;
import static com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.enums.ErrorType.SYSTEM;
import static com.fernando.ms.clients.app.dfood_clients_service.infrastructure.utils.ErrorCatalog.*;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CustomerNotFoundException.class)
    public ErrorResponse handleProductNotFoundException() {
        return ErrorResponse.builder()
                .code(CUSTOMER_NOT_FOUND.getCode())
                .type(FUNCTIONAL)
                .message(CUSTOMER_NOT_FOUND.getMessage())
                .timestamp(LocalDate.now().toString())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AddressNotFoundException.class)
    public ErrorResponse handleAddressNotFoundException() {
        return ErrorResponse.builder()
                .code(CUSTOMERS_ADDRESS_NOT_FOUND.getCode())
                .type(FUNCTIONAL)
                .message(CUSTOMERS_ADDRESS_NOT_FOUND.getMessage())
                .timestamp(LocalDate.now().toString())
                .build();
    }



    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        return ErrorResponse.builder()
                .code(CUSTOMERS_BAD_PARAMETERS.getCode())
                .type(FUNCTIONAL)
                .message(CUSTOMERS_BAD_PARAMETERS.getMessage())
                .details(bindingResult.getFieldErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList())
                .timestamp(LocalDate.now().toString())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomerEmailAlreadyExistsException.class)
    public ErrorResponse handleClientEmailAlreadyExistsException(CustomerEmailAlreadyExistsException e) {
        return ErrorResponse.builder()
                .code(CUSTOMERS_EMAIL_USER_ALREADY_EXISTS.getCode())
                .type(FUNCTIONAL)
                .message(CUSTOMERS_EMAIL_USER_ALREADY_EXISTS.getMessage())
                .timestamp(LocalDate.now().toString())
                .details(Collections.singletonList(e.getMessage()))
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(Exception e) {

        return ErrorResponse.builder()
                .code(INTERNAL_SERVER_ERROR.getCode())
                .type(SYSTEM)
                .message(INTERNAL_SERVER_ERROR.getMessage())
                .details(Collections.singletonList(e.getMessage()))
                .timestamp(LocalDate.now().toString())
                .build();
    }
}
