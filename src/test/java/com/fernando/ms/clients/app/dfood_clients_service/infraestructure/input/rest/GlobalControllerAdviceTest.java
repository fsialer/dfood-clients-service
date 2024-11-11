package com.fernando.ms.clients.app.dfood_clients_service.infraestructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fernando.ms.clients.app.dfood_clients_service.application.ports.input.ClientInputPort;
import com.fernando.ms.clients.app.dfood_clients_service.domain.exceptions.ClientEmailAlreadyExistsException;
import com.fernando.ms.clients.app.dfood_clients_service.domain.exceptions.ClientNotFoundException;
import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Client;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.ClientRestAdapter;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.mapper.ClientRestMapper;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.request.CreateClientRequest;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.response.ErrorResponse;
import com.fernando.ms.clients.app.dfood_clients_service.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.enums.ErrorType.FUNCTIONAL;
import static com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.enums.ErrorType.SYSTEM;
import static com.fernando.ms.clients.app.dfood_clients_service.infrastructure.utils.ErrorCatalog.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {ClientRestAdapter.class})
public class GlobalControllerAdviceTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClientInputPort clientInputPort;

    @MockBean
    private ClientRestMapper clientRestMapper;

    @Test
    void whenThrowsProductNotFoundExceptionThenReturnNotFound() throws Exception {
        when(clientInputPort.findById(anyLong()))
                .thenThrow(new ClientNotFoundException());
        mockMvc.perform(get("/clients/{id}",2L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result->{
                    ErrorResponse errorResponse=objectMapper.readValue(
                            result.getResponse().getContentAsString(), ErrorResponse.class);
                    assertAll(
                            ()->assertEquals(CLIENTS_NOT_FOUND.getCode(),errorResponse.getCode()),
                            ()->assertEquals(FUNCTIONAL,errorResponse.getType()),
                            ()->assertEquals(CLIENTS_NOT_FOUND.getMessage(),errorResponse.getMessage()),
                            ()->assertNotNull(errorResponse.getTimestamp())
                    );
                });
    }

    @Test
    void whenThrowsMethodArgumentNotValidExceptionThenReturnBadRequest() throws Exception {

        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    ErrorResponse errorResponse = objectMapper.readValue(
                            result.getResponse().getContentAsString(), ErrorResponse.class);
                    assertAll(
                            () -> assertThat(errorResponse.getCode()).isEqualTo(CLIENTS_BAD_PARAMETERS.getCode()),
                            () -> assertThat(errorResponse.getType()).isEqualTo(FUNCTIONAL),
                            () -> assertThat(errorResponse.getMessage()).isEqualTo(CLIENTS_BAD_PARAMETERS.getMessage()),
                            () -> assertThat(errorResponse.getDetails()).isNotNull(),
                            () -> assertThat(errorResponse.getTimestamp()).isNotNull()
                    );
                })
                .andDo(print());
    }


    @Test
    void whenThrowsGenericExceptionThenReturnInternalServerErrorResponse() throws Exception {
        when(clientInputPort.findAll())
                .thenThrow(new RuntimeException("Generic error"));

        mockMvc.perform(get("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(result -> {
                    ErrorResponse errorResponse = objectMapper.readValue(
                            result.getResponse().getContentAsString(), ErrorResponse.class);
                    assertThat(errorResponse.getCode()).isEqualTo(INTERNAL_SERVER_ERROR.getCode());
                    assertThat(errorResponse.getType()).isEqualTo(SYSTEM);
                    assertThat(errorResponse.getMessage()).isEqualTo(INTERNAL_SERVER_ERROR.getMessage());
                    assertThat(errorResponse.getDetails().get(0)).isEqualTo("Generic error");
                    assertThat(errorResponse.getTimestamp()).isNotNull();
                })
                .andDo(print());
    }

    @Test
    void whenThrowsClientEmailUserAlreadyExistsExceptionThenReturnBadRequest() throws Exception {
        CreateClientRequest rq = TestUtils.buildClientCreateRequestMock();
        Client client = TestUtils.buildClientMock();

        when(clientRestMapper.toClient(any(CreateClientRequest.class)))
                .thenReturn(client);

        when(clientInputPort.save(any(Client.class)))
                .thenThrow(new ClientEmailAlreadyExistsException(rq.getEmail()));

        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rq)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    ErrorResponse errorResponse = objectMapper.readValue(
                            result.getResponse().getContentAsString(), ErrorResponse.class);
                    assertEquals(CLIENTS_EMAIL_USER_ALREADY_EXISTS.getCode(), errorResponse.getCode());
                    assertEquals(FUNCTIONAL, errorResponse.getType());
                    assertEquals(CLIENTS_EMAIL_USER_ALREADY_EXISTS.getMessage(), errorResponse.getMessage());
                    assertEquals("Client email: " + rq.getEmail() + " already exists!", errorResponse.getDetails().get(0));
                    assertNotNull(errorResponse.getTimestamp());
                })
                .andDo(print());
    }
}
