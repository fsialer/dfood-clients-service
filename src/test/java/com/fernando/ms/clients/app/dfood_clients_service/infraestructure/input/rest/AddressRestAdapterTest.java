package com.fernando.ms.clients.app.dfood_clients_service.infraestructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fernando.ms.clients.app.dfood_clients_service.application.ports.input.AddressInputPort;
import com.fernando.ms.clients.app.dfood_clients_service.application.ports.input.ClientInputPort;
import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Address;
import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Client;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.AddressRestAdapter;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.ClientRestAdapter;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.mapper.AddressRestMapper;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.mapper.ClientRestMapper;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.response.AddressResponse;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.response.ClientResponse;
import com.fernando.ms.clients.app.dfood_clients_service.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    void shouldReturnAListAddressWhenHaveData() throws Exception {

        Address address= TestUtils.buildAddressMock();
        List<AddressResponse> addressesResponse= Collections.singletonList(TestUtils.buildAddressResponseMock());

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

}
