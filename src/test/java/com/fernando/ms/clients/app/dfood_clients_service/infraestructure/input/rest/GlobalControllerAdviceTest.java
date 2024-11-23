package com.fernando.ms.clients.app.dfood_clients_service.infraestructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fernando.ms.clients.app.dfood_clients_service.application.ports.input.AddressInputPort;
import com.fernando.ms.clients.app.dfood_clients_service.application.ports.input.ClientInputPort;
import com.fernando.ms.clients.app.dfood_clients_service.domain.exceptions.AddressNotFoundException;
import com.fernando.ms.clients.app.dfood_clients_service.domain.exceptions.ClientEmailAlreadyExistsException;
import com.fernando.ms.clients.app.dfood_clients_service.domain.exceptions.ClientNotFoundException;
import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Client;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.AddressRestAdapter;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.ClientRestAdapter;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.mapper.AddressRestMapper;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.mapper.ClientRestMapper;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.request.CreateClientRequest;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.response.ErrorResponse;
import com.fernando.ms.clients.app.dfood_clients_service.utils.TestUtilsClient;
import org.junit.jupiter.api.DisplayName;
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

@WebMvcTest(controllers = {ClientRestAdapter.class, AddressRestAdapter.class})
public class GlobalControllerAdviceTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClientInputPort clientInputPort;

    @MockBean
    private AddressInputPort addressInputPort;

    @MockBean
    private AddressRestMapper addressRestMapper;

    @MockBean
    private ClientRestMapper clientRestMapper;

    @Test
    @DisplayName("Expect ClientNotFoundException When Customer Identifier Is Unknown")
    void Expect_ClientNotFoundException_When_CustomerIdentifierIsUnknown() throws Exception {
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
    @DisplayName("Expect MethodArgumentNotValidException When Customer Information Is Invalid")
    void Expect_MethodArgumentNotValidException_When_CustomerInformationIsInvalid() throws Exception {
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
    @DisplayName("Expect RuntimeException When Customer Information Is Invalid")
    void Expect_RuntimeException_When_CustomerInformationIsInvalid() throws Exception {
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
    @DisplayName("Expect ClientEmailAlreadyExistsException When Customer Email Already Exists")
    void Expect_ClientEmailAlreadyExistsException_When_CustomerEmailAlreadyExists() throws Exception {
        CreateClientRequest rq = TestUtilsClient.buildClientCreateRequestMock();
        Client client = TestUtilsClient.buildClientMock();

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

    @Test
    @DisplayName("Expect AddressNotFoundException When Address Identifier Is Unknown")
    void Expect_AddressNotFoundException_When_AddressIdentifierIsUnknown() throws Exception {
        when(addressInputPort.findById(anyLong()))
                .thenThrow(new AddressNotFoundException());
        mockMvc.perform(get("/addresses/{id}",2L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result->{
                    ErrorResponse errorResponse=objectMapper.readValue(
                            result.getResponse().getContentAsString(), ErrorResponse.class);
                    assertAll(
                            ()->assertEquals(CLIENTS_ADDRESS_NOT_FOUND.getCode(),errorResponse.getCode()),
                            ()->assertEquals(FUNCTIONAL,errorResponse.getType()),
                            ()->assertEquals(CLIENTS_ADDRESS_NOT_FOUND.getMessage(),errorResponse.getMessage()),
                            ()->assertNotNull(errorResponse.getTimestamp())
                    );
                });
    }

}
