package com.fernando.ms.clients.app.dfood_clients_service.infrastructure.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCatalog {
    CLIENTS_NOT_FOUND("CLIENTS_MS_001", "Client not found."),
    CLIENTS_BAD_PARAMETERS("CLIENTS_MS_002", "Invalid parameters for creation."),
    CLIENTS_EMAIL_USER_ALREADY_EXISTS("CLIENTS_MS_003", "Email already exists."),
    INTERNAL_SERVER_ERROR("CLIENTS_MS_000", "Internal server error.");

    private final String code;
    private final String message;
}
