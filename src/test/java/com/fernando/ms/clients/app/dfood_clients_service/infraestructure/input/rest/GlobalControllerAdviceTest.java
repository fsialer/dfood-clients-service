package com.fernando.ms.clients.app.dfood_clients_service.infraestructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fernando.ms.clients.app.dfood_clients_service.application.ports.input.AddressInputPort;
import com.fernando.ms.clients.app.dfood_clients_service.application.ports.input.CustomerInputPort;
import com.fernando.ms.clients.app.dfood_clients_service.domain.exceptions.AddressNotFoundException;
import com.fernando.ms.clients.app.dfood_clients_service.domain.exceptions.CustomerEmailAlreadyExistsException;
import com.fernando.ms.clients.app.dfood_clients_service.domain.exceptions.CustomerNotFoundException;
import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Customer;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.AddressRestAdapter;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.CustomerRestAdapter;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.mapper.AddressRestMapper;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.mapper.CustomerRestMapper;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.request.CreateCustomerRequest;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.response.ErrorResponse;
import com.fernando.ms.clients.app.dfood_clients_service.utils.TestUtilsCustomer;
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

@WebMvcTest(controllers = {CustomerRestAdapter.class, AddressRestAdapter.class})
public class GlobalControllerAdviceTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerInputPort customerInputPort;

    @MockBean
    private AddressInputPort addressInputPort;

    @MockBean
    private AddressRestMapper addressRestMapper;

    @MockBean
    private CustomerRestMapper customerRestMapper;

    @Test
    @DisplayName("Expect CustomerNotFoundException When Customer Identifier Is Unknown")
    void Expect_CustomerNotFoundException_When_CustomerIdentifierIsUnknown() throws Exception {
        when(customerInputPort.findById(anyLong()))
                .thenThrow(new CustomerNotFoundException());
        mockMvc.perform(get("/customers/{id}",2L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result->{
                    ErrorResponse errorResponse=objectMapper.readValue(
                            result.getResponse().getContentAsString(), ErrorResponse.class);
                    assertAll(
                            ()->assertEquals(CUSTOMER_NOT_FOUND.getCode(),errorResponse.getCode()),
                            ()->assertEquals(FUNCTIONAL,errorResponse.getType()),
                            ()->assertEquals(CUSTOMER_NOT_FOUND.getMessage(),errorResponse.getMessage()),
                            ()->assertNotNull(errorResponse.getTimestamp())
                    );
                });
    }

    @Test
    @DisplayName("Expect MethodArgumentNotValidException When Customer Information Is Invalid")
    void Expect_MethodArgumentNotValidException_When_CustomerInformationIsInvalid() throws Exception {
        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    ErrorResponse errorResponse = objectMapper.readValue(
                            result.getResponse().getContentAsString(), ErrorResponse.class);
                    assertAll(
                            () -> assertThat(errorResponse.getCode()).isEqualTo(CUSTOMERS_BAD_PARAMETERS.getCode()),
                            () -> assertThat(errorResponse.getType()).isEqualTo(FUNCTIONAL),
                            () -> assertThat(errorResponse.getMessage()).isEqualTo(CUSTOMERS_BAD_PARAMETERS.getMessage()),
                            () -> assertThat(errorResponse.getDetails()).isNotNull(),
                            () -> assertThat(errorResponse.getTimestamp()).isNotNull()
                    );
                })
                .andDo(print());
    }


    @Test
    @DisplayName("Expect RuntimeException When Customer Information Is Invalid")
    void Expect_RuntimeException_When_CustomerInformationIsInvalid() throws Exception {
        when(customerInputPort.findAll())
                .thenThrow(new RuntimeException("Generic error"));

        mockMvc.perform(get("/customers")
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
    @DisplayName("Expect CustomerEmailAlreadyExistsException When Customer Email Already Exists")
    void Expect_CustomerEmailAlreadyExistsException_When_CustomerEmailAlreadyExists() throws Exception {
        CreateCustomerRequest rq = TestUtilsCustomer.buildCustomerCreateRequestMock();
        Customer customer = TestUtilsCustomer.buildCustomerMock();

        when(customerRestMapper.toCustomer(any(CreateCustomerRequest.class)))
                .thenReturn(customer);

        when(customerInputPort.save(any(Customer.class)))
                .thenThrow(new CustomerEmailAlreadyExistsException(rq.getEmail()));

        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rq)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    ErrorResponse errorResponse = objectMapper.readValue(
                            result.getResponse().getContentAsString(), ErrorResponse.class);
                    assertEquals(CUSTOMERS_EMAIL_USER_ALREADY_EXISTS.getCode(), errorResponse.getCode());
                    assertEquals(FUNCTIONAL, errorResponse.getType());
                    assertEquals(CUSTOMERS_EMAIL_USER_ALREADY_EXISTS.getMessage(), errorResponse.getMessage());
                    assertEquals("Customer email: " + rq.getEmail() + " already exists!", errorResponse.getDetails().get(0));
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
                            ()->assertEquals(CUSTOMERS_ADDRESS_NOT_FOUND.getCode(),errorResponse.getCode()),
                            ()->assertEquals(FUNCTIONAL,errorResponse.getType()),
                            ()->assertEquals(CUSTOMERS_ADDRESS_NOT_FOUND.getMessage(),errorResponse.getMessage()),
                            ()->assertNotNull(errorResponse.getTimestamp())
                    );
                });
    }

}
