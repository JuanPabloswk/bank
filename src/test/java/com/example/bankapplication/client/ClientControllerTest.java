package com.example.bankapplication.client;

import com.example.bankapplication.controller.ClientController;
import com.example.bankapplication.dto.request.client.ClientCreateDTO;
import com.example.bankapplication.dto.request.client.ClientUpdateDTO;
import com.example.bankapplication.dto.response.client.ClientResponseDTO;
import com.example.bankapplication.mapper.ClientMapper;
import com.example.bankapplication.model.Client;
import com.example.bankapplication.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

    @Mock
    private ClientService clientService;

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientController clientController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    private void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
    }

    @Test
    void createClient_shouldReturnCreatedClient() throws Exception {
        setup();

        ClientCreateDTO createDTO = new ClientCreateDTO(null, null, null, null, null, null );
        createDTO.setBirthDate(LocalDate.of(2000, 1, 1));

        Client client = new Client();
        ClientResponseDTO responseDTO = new ClientResponseDTO();

        when(clientService.createClient(createDTO)).thenReturn(client);
        when(clientMapper.responseDTO(client)).thenReturn(responseDTO);

        mockMvc.perform(post("/api/clients/create-client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(responseDTO)));
    }

    @Test
    void getClientId_shouldReturnClient() throws Exception {
        setup();

        Client client = new Client();
        when(clientService.getClientId(1L)).thenReturn(client);

        mockMvc.perform(get("/api/clients/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllClients_shouldReturnClientList() throws Exception {
        setup();

        when(clientService.getAllClients()).thenReturn(List.of(new Client(), new Client()));

        mockMvc.perform(get("/api/clients/getAllClients"))
                .andExpect(status().isOk());
    }

    @Test
    void updateClient_shouldReturnUpdatedClient() throws Exception {
        setup();

        ClientUpdateDTO updateDTO = new ClientUpdateDTO();
        Client updatedClient = new Client();

        when(clientService.updateClient(eq(1L), any(ClientUpdateDTO.class))).thenReturn(updatedClient);

        mockMvc.perform(put("/api/clients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteClient_shouldReturnNoContent() throws Exception {
        setup();

        doNothing().when(clientService).deleteClient(1L);

        mockMvc.perform(delete("/api/clients/1"))
                .andExpect(status().isNoContent());
    }
}