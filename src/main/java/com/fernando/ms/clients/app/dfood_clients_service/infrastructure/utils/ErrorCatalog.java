package com.fernando.ms.clients.app.dfood_clients_service.infrastructure.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCatalog {
    CLIENT_NOT_FOUND("CLIENTS_MS_001", "Client not found.");

    private final String code;
    private final String message;
}
