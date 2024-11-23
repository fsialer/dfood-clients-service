package com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAddressRequest {
    @NotBlank(message = "Field street cannot be null or blank.")
    private String street;
    @NotNull(message = "Field number cannot be null.")
    private Integer number;
    @NotNull(message = "Field selected cannot be null.")
    private Boolean selected;
    private Long customerId;
}
