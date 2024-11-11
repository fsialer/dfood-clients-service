package com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateClientRequest {
    @NotBlank(message = "Field name cannot be null or blank.")
    private String name;
    @NotBlank(message = "Field lastname cannot be null or blank.")
    private String lastname;
    @NotBlank(message = "Field fullName cannot be null or blank.")
    private String phone;
    @NotBlank(message = "Field email cannot be null or blank.")
    @Email(message = "Field email do not valid.")
    private String email;
    private Long userId;
}
