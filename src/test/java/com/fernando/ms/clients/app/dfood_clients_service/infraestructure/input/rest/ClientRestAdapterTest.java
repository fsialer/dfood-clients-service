package com.fernando.ms.clients.app.dfood_clients_service.infraestructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fernando.ms.clients.app.dfood_clients_service.application.ports.input.ClientInputPort;
import com.fernando.ms.clients.app.dfood_clients_service.domain.models.Client;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.ClientRestAdapter;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.mapper.ClientRestMapper;
import com.fernando.ms.clients.app.dfood_clients_service.infrastructure.adapter.input.rest.models.request.CreateClientRequest;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientRestAdapter.class)
public class ClientRestAdapterTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ClientInputPort clientInputPort;

    @MockBean
    private ClientRestMapper clientRestMapper;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        objectMapper=new ObjectMapper();
    }

    @Test
    void shouldReturnAListClientsWhenHaveData() throws Exception {

        Client client= TestUtils.buildClientMock();
        List<ClientResponse> clientsResponse= Collections.singletonList(TestUtils.buildClientResponseMock());

        when(clientInputPort.findAll())
                .thenReturn(Collections.singletonList(client));

        when(clientRestMapper.toClientsResponse(anyList()))
                .thenReturn(clientsResponse);

        mockMvc.perform(get("/clients").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.length()").value(1))
                .andDo(print());

        Mockito.verify(clientInputPort,times(1)).findAll();
        Mockito.verify(clientRestMapper,times(1)).toClientsResponse(anyList());
    }

    @Test
    void shouldReturnAClientWhenFindById() throws Exception {

        Client client= TestUtils.buildClientMock();
        ClientResponse clientResponse= TestUtils.buildClientResponseMock();

        when(clientInputPort.findById(anyLong()))
                .thenReturn(client);

        when(clientRestMapper.toClientResponse(any(Client.class)))
                .thenReturn(clientResponse);

        mockMvc.perform(get("/clients/{id}",1L).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andDo(print());

        Mockito.verify(clientInputPort,times(1)).findById(anyLong());
        Mockito.verify(clientRestMapper,times(1)).toClientResponse(any(Client.class));
    }

    @Test
    void shouldReturnAClientWhenSaveANewUser() throws Exception {
        ClientResponse clientResponse= TestUtils.buildClientResponseMock();
        Client client= TestUtils.buildClientMock();
        CreateClientRequest rq =TestUtils.buildClientCreateRequestMock();

        when(clientInputPort.save(any(Client.class)))
                .thenReturn(client);
        when(clientRestMapper.toClientResponse(any(Client.class)))
                .thenReturn(clientResponse);
        when(clientRestMapper.toClient(any(CreateClientRequest.class))).thenReturn(client);

        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rq)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isNotEmpty())
                //.andExpect(jsonPath("$").value(clientResponse))
                .andExpect(jsonPath("$.id").value(1L))
                .andDo(print());

        Mockito.verify(clientInputPort,times(1)).save(any(Client.class));
        Mockito.verify(clientRestMapper,times(1)).toClientResponse(any(Client.class));
        Mockito.verify(clientRestMapper,times(1)).toClient(any(CreateClientRequest.class));
    }

    @Test
    void shouldReturnAClientWhenIntoAnUserToUpdate() throws Exception {
        ClientResponse clientResponse= TestUtils.buildClientResponseMock();
        Client client= TestUtils.buildClientMock();
        CreateClientRequest rq =TestUtils.buildClientCreateRequestMock();

        when(clientInputPort.update(anyLong(),any(Client.class)))
                .thenReturn(client);
        when(clientRestMapper.toClientResponse(any(Client.class)))
                .thenReturn(clientResponse);
        when(clientRestMapper.toClient(any(CreateClientRequest.class))).thenReturn(client);

        mockMvc.perform(put("/clients/{id}",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                //.andExpect(jsonPath("$").value(clientResponse))
                .andExpect(jsonPath("$.id").value(1L))
                .andDo(print());

        Mockito.verify(clientInputPort,times(1)).update(anyLong(),any(Client.class));
        Mockito.verify(clientRestMapper,times(1)).toClientResponse(any(Client.class));
        Mockito.verify(clientRestMapper,times(1)).toClient(any(CreateClientRequest.class));
    }

    @Test
    void shouldReturnClientInactiveWhenInactiveClientById() throws Exception {

        Client user= TestUtils.buildClientInactiveMock();
        ClientResponse clientResponse= TestUtils.buildClientInactiveResponseMock();
        when(clientInputPort.inactive(anyLong()))
                .thenReturn(user);
        when(clientRestMapper.toClientResponse(any(Client.class)))
                .thenReturn(clientResponse);
        mockMvc.perform(put("/clients/{id}/inactive",1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusClient").value("INACTIVE"))
                .andDo(print());
        Mockito.verify(clientInputPort,times(1)).inactive(anyLong());

        Mockito.verify(clientRestMapper,times(1)).toClientResponse(any(Client.class));

    }

}
