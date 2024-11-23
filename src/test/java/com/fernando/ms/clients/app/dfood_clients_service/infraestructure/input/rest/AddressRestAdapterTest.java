package com.fernando.ms.clients.app.dfood_clients_service.infraestructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fernando.ms.clients.app.dfood_clients_service.application.ports.input.AddressInputPort;
import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Address;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.AddressRestAdapter;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.mapper.AddressRestMapper;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.request.CreateAddressRequest;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.response.AddressResponse;
import com.fernando.ms.clients.app.dfood_clients_service.utils.TestUtilsAddress;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AddressRestAdapter.class)
public class AddressRestAdapterTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private AddressInputPort addressInputPort;

    @MockBean
    private AddressRestMapper addressRestMapper;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        objectMapper=new ObjectMapper();
    }

    @Test
    @DisplayName("When Addresses Is Availability Expect Addresses Information Successfully")
    void When_AddressesIsAvailability_Expect_AddressesInformationSuccessfully() throws Exception {
        Address address= TestUtilsAddress.buildAddressMock();
        List<AddressResponse> addressesResponse= Collections.singletonList(TestUtilsAddress.buildAddressResponseMock());

        when(addressInputPort.findAll())
                .thenReturn(Collections.singletonList(address));

        when(addressRestMapper.toAddressesResponse(anyList()))
                .thenReturn(addressesResponse);

        mockMvc.perform(get("/addresses").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.length()").value(1))
                .andDo(print());

        Mockito.verify(addressInputPort,times(1)).findAll();
        Mockito.verify(addressRestMapper,times(1)).toAddressesResponse(anyList());
    }

    @Test
    @DisplayName("When Address Identifier Is Correct Expect Address Information Successfully")
    void When_AddressIdentifierIsCorrect_Expect_AddressInformationSuccessfully() throws Exception {

        Address address= TestUtilsAddress.buildAddressMock();
        AddressResponse addressResponse= TestUtilsAddress.buildAddressResponseMock();

        when(addressInputPort.findById(anyLong()))
                .thenReturn(address);

        when(addressRestMapper.toAddressResponse(any(Address.class)))
                .thenReturn(addressResponse);

        mockMvc.perform(get("/addresses/{id}",1L).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andDo(print());

        Mockito.verify(addressInputPort,times(1)).findById(anyLong());
        Mockito.verify(addressRestMapper,times(1)).toAddressResponse(any(Address.class));
    }

    @Test
    @DisplayName("When AddressInformationIsCorrect Expect Address Information To Be Saved Successfully")
    void When_AddressInformationIsCorrect_Expect_AddressInformationToBeSavedSuccessfully() throws Exception {
        AddressResponse addressResponse= TestUtilsAddress.buildAddressResponseMock();
        Address address= TestUtilsAddress.buildAddressMock();
        CreateAddressRequest rq = TestUtilsAddress.buildCreateAddressRequest();

        when(addressInputPort.save(any(Address.class)))
                .thenReturn(address);
        when(addressRestMapper.toAddressResponse(any(Address.class)))
                .thenReturn(addressResponse);
        when(addressRestMapper.toAddress(any(CreateAddressRequest.class))).thenReturn(address);

        mockMvc.perform(post("/addresses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rq)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isNotEmpty())
                //.andExpect(jsonPath("$").value(clientResponse))
                .andExpect(jsonPath("$.id").value(1L))
                .andDo(print());

        Mockito.verify(addressInputPort,times(1)).save(any(Address.class));
        Mockito.verify(addressRestMapper,times(1)).toAddressResponse(any(Address.class));
        Mockito.verify(addressRestMapper,times(1)).toAddress(any(CreateAddressRequest.class));
    }

    @Test
    @DisplayName("When Address Information Is Correct Expect Address Information To Be Updated Successfully")
    void When_AddressInformationIsCorrect_Expect_AddressInformationToBeUpdatedSuccessfully() throws Exception {
        AddressResponse addressResponse= TestUtilsAddress.buildAddressResponseMock();
        Address address= TestUtilsAddress.buildAddressMock();
        CreateAddressRequest rq = TestUtilsAddress.buildCreateAddressRequest();

        when(addressInputPort.update(anyLong(),any(Address.class)))
                .thenReturn(address);
        when(addressRestMapper.toAddressResponse(any(Address.class)))
                .thenReturn(addressResponse);
        when(addressRestMapper.toAddress(any(CreateAddressRequest.class))).thenReturn(address);

        mockMvc.perform(put("/addresses/{id}",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                //.andExpect(jsonPath("$").value(clientResponse))
                .andExpect(jsonPath("$.id").value(1L))
                .andDo(print());

        Mockito.verify(addressInputPort,times(1)).update(anyLong(),any(Address.class));
        Mockito.verify(addressRestMapper,times(1)).toAddressResponse(any(Address.class));
        Mockito.verify(addressRestMapper,times(1)).toAddress(any(CreateAddressRequest.class));
    }

    @Test
    @DisplayName("When Address Identifier Is Correct Expect Address Information To Be Deleted Successfully")
    void When_AddressIdentifierIsCorrect_Expect_AddressInformationToBeDeletedSuccessfully() throws Exception {
        doNothing().when(addressInputPort).delete(anyLong());

        mockMvc.perform(delete("/addresses/{id}",1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
        Mockito.verify(addressInputPort,times(1)).delete(anyLong());

    }

}
