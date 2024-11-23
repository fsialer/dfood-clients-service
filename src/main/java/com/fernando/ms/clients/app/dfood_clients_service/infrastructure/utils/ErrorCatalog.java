package com.fernando.ms.clients.app.dfood_clients_service.infrastructure.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCatalog {
    CUSTOMER_NOT_FOUND("CUSTOMERS_MS_001", "Client not found."),
    CUSTOMERS_BAD_PARAMETERS("CUSTOMERS_MS_002", "Invalid parameters for creation."),
    CUSTOMERS_EMAIL_USER_ALREADY_EXISTS("CUSTOMERS_MS_003", "Email already exists."),
    CUSTOMERS_ADDRESS_NOT_FOUND("CUSTOMERS_MS_004", "Address not found."),
    INTERNAL_SERVER_ERROR("CUSTOMERS_MS_000", "Internal server error.");

    private final String code;
    private final String message;
}
