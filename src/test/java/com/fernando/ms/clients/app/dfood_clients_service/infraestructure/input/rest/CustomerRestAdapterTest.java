package com.fernando.ms.clients.app.dfood_clients_service.infraestructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fernando.ms.clients.app.dfood_clients_service.application.ports.input.CustomerInputPort;
import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Customer;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.CustomerRestAdapter;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.mapper.CustomerRestMapper;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.request.CreateCustomerRequest;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.response.CustomerResponse;
import com.fernando.ms.clients.app.dfood_clients_service.utils.TestUtilsCustomer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerRestAdapter.class)
public class CustomerRestAdapterTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private CustomerInputPort customerInputPort;

    @MockBean
    private CustomerRestMapper customerRestMapper;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        objectMapper=new ObjectMapper();
    }

    @Test
    @DisplayName("When Customers Is Availability Expect Customers Information Successfully")
    void When_CustomersIsAvailability_Expect_CustomersInformationSuccessfully() throws Exception {

        Customer customer = TestUtilsCustomer.buildCustomerMock();
        List<CustomerResponse> clientsResponse= Collections.singletonList(TestUtilsCustomer.buildCustomerResponseMock());

        when(customerInputPort.findAll())
                .thenReturn(Collections.singletonList(customer));

        when(customerRestMapper.toCustomersResponse(anyList()))
                .thenReturn(clientsResponse);

        mockMvc.perform(get("/clients").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.length()").value(1))
                .andDo(print());

        Mockito.verify(customerInputPort,times(1)).findAll();
        Mockito.verify(customerRestMapper,times(1)).toCustomersResponse(anyList());
    }

    @Test
    @DisplayName("When Customer Identifier Is Correct Expect Customer Information Successfully")
    void When_CustomerIdentifierIsCorrect_Expect_CustomerInformationSuccessfully() throws Exception {

        Customer customer = TestUtilsCustomer.buildCustomerMock();
        CustomerResponse customerResponse = TestUtilsCustomer.buildCustomerResponseMock();

        when(customerInputPort.findById(anyLong()))
                .thenReturn(customer);

        when(customerRestMapper.toCustomerResponse(any(Customer.class)))
                .thenReturn(customerResponse);

        mockMvc.perform(get("/clients/{id}",1L).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andDo(print());

        Mockito.verify(customerInputPort,times(1)).findById(anyLong());
        Mockito.verify(customerRestMapper,times(1)).toCustomerResponse(any(Customer.class));
    }

    @Test
    @DisplayName("When_CustomerInformationIsCorrect_Expect_CustomerInformationToBeSavedSuccessfully")
    void When_CustomerInformationIsCorrect_Expect_CustomerInformationToBeSavedSuccessfully() throws Exception {
        CustomerResponse customerResponse = TestUtilsCustomer.buildCustomerResponseMock();
        Customer customer = TestUtilsCustomer.buildCustomerMock();
        CreateCustomerRequest rq = TestUtilsCustomer.buildCustomerCreateRequestMock();

        when(customerInputPort.save(any(Customer.class)))
                .thenReturn(customer);
        when(customerRestMapper.toCustomerResponse(any(Customer.class)))
                .thenReturn(customerResponse);
        when(customerRestMapper.toCustomer(any(CreateCustomerRequest.class))).thenReturn(customer);

        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rq)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isNotEmpty())
                //.andExpect(jsonPath("$").value(clientResponse))
                .andExpect(jsonPath("$.id").value(1L))
                .andDo(print());

        Mockito.verify(customerInputPort,times(1)).save(any(Customer.class));
        Mockito.verify(customerRestMapper,times(1)).toCustomerResponse(any(Customer.class));
        Mockito.verify(customerRestMapper,times(1)).toCustomer(any(CreateCustomerRequest.class));
    }

    @Test
    @DisplayName("When_CustomerInformationIsCorrect_Expect_CustomerInformationToBeSavedSuccessfully")
    void When_CustomerAndAddressInformationIsCorrect_Expect_CustomerAndAddressInformationToBeSavedSuccessfully() throws Exception {
        CustomerResponse customerResponse = TestUtilsCustomer.buildCustomerResponseMock();
        Customer customer = TestUtilsCustomer.buildCustomerMock();
        CreateCustomerRequest rq = TestUtilsCustomer.buildCustomerAddressCreateRequestMock();

        when(customerInputPort.save(any(Customer.class)))
                .thenReturn(customer);
        when(customerRestMapper.toCustomerResponse(any(Customer.class)))
                .thenReturn(customerResponse);
        when(customerRestMapper.toCustomer(any(CreateCustomerRequest.class))).thenReturn(customer);

        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rq)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isNotEmpty())
                //.andExpect(jsonPath("$").value(clientResponse))
                .andExpect(jsonPath("$.id").value(1L))
                .andDo(print());

        Mockito.verify(customerInputPort,times(1)).save(any(Customer.class));
        Mockito.verify(customerRestMapper,times(1)).toCustomerResponse(any(Customer.class));
        Mockito.verify(customerRestMapper,times(1)).toCustomer(any(CreateCustomerRequest.class));
    }

    @Test
    @DisplayName("When Customer Information Is Correct Expect Customer Information To Be Updated Successfully")
    void When_CustomerInformationIsCorrect_Expect_CustomerInformationToBeUpdatedSuccessfully() throws Exception {
        CustomerResponse customerResponse = TestUtilsCustomer.buildCustomerResponseMock();
        Customer customer = TestUtilsCustomer.buildCustomerMock();
        CreateCustomerRequest rq = TestUtilsCustomer.buildCustomerCreateRequestMock();

        when(customerInputPort.update(anyLong(),any(Customer.class)))
                .thenReturn(customer);
        when(customerRestMapper.toCustomerResponse(any(Customer.class)))
                .thenReturn(customerResponse);
        when(customerRestMapper.toCustomer(any(CreateCustomerRequest.class))).thenReturn(customer);

        mockMvc.perform(put("/clients/{id}",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.id").value(1L))
                .andDo(print());

        Mockito.verify(customerInputPort,times(1)).update(anyLong(),any(Customer.class));
        Mockito.verify(customerRestMapper,times(1)).toCustomerResponse(any(Customer.class));
        Mockito.verify(customerRestMapper,times(1)).toCustomer(any(CreateCustomerRequest.class));
    }

    @Test
    @DisplayName("When Customer Identifier Is Correct Expect Customer Information To Be Inactive")
    void When_CustomerIdentifierIsCorrect_Expect_CustomerInformationToBeInactive() throws Exception {

        Customer user= TestUtilsCustomer.buildCustomerInactiveMock();
        CustomerResponse customerResponse = TestUtilsCustomer.buildCustomerInactiveResponseMock();
        when(customerInputPort.inactive(anyLong()))
                .thenReturn(user);
        when(customerRestMapper.toCustomerResponse(any(Customer.class)))
                .thenReturn(customerResponse);
        mockMvc.perform(put("/clients/{id}/inactive",1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCustomer").value("INACTIVE"))
                .andDo(print());
        Mockito.verify(customerInputPort,times(1)).inactive(anyLong());

        Mockito.verify(customerRestMapper,times(1)).toCustomerResponse(any(Customer.class));

    }

    @Test
    @DisplayName("When Customer Identifier Is Correct Expect Customer Information To Be Deleted Successfully")
    void When_CustomerIdentifierIsCorrect_Expect_CustomerInformationToBeDeletedSuccessfully() throws Exception {

        Customer user= TestUtilsCustomer.buildCustomerInactiveMock();
        CustomerResponse customerResponse = TestUtilsCustomer.buildCustomerInactiveResponseMock();
        doNothing().when(customerInputPort).delete(anyLong());

        mockMvc.perform(delete("/clients/{id}",1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
        Mockito.verify(customerInputPort,times(1)).delete(anyLong());

    }


}
