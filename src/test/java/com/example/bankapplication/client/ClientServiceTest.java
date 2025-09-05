package com.example.bankapplication.client;

import com.example.bankapplication.dto.request.client.ClientCreateDTO;
import com.example.bankapplication.dto.request.client.ClientUpdateDTO;
import com.example.bankapplication.mapper.ClientMapper;
import com.example.bankapplication.model.Client;
import com.example.bankapplication.repository.ClientRepository;
import com.example.bankapplication.service.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientService clientService;

    @Test
    void createClient_shouldSave_whenAdult() {
        ClientCreateDTO dto = new ClientCreateDTO();
        dto.setBirthDate(LocalDate.now().minusYears(20));

        Client client = new Client();
        when(clientMapper.createClient(dto)).thenReturn(client);

        clientService.createClient(dto);

        verify(clientRepository).save(client);
    }

    @Test
    void createClient_shouldThrow_whenMinor() {
        ClientCreateDTO dto = new ClientCreateDTO();
        dto.setBirthDate(LocalDate.now().minusYears(15));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> clientService.createClient(dto));

        assertEquals("You should be over 18 years old", ex.getMessage());
        verify(clientRepository, never()).save(any());
    }

    @Test
    void getClientId_shouldReturnClient_whenFound() {
        Client client = new Client();
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        Client result = clientService.getClientId(1L);

        assertEquals(client, result);
    }

    @Test
    void getClientId_shouldThrow_whenNotFound() {
        when(clientRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> clientService.getClientId(99L));
    }

    @Test
    void updateClient_shouldUpdate_whenFound() {
        ClientUpdateDTO dto = new ClientUpdateDTO();
        dto.setNames("NewName");

        Client client = new Client();
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        Client result = clientService.updateClient(1L, dto);

        assertEquals("NewName", result.getNames());
        verify(clientRepository).save(client);
    }

    @Test
    void deleteClient_shouldDelete_whenFound() {
        Client client = new Client();
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        clientService.deleteClient(1L);

        verify(clientRepository).delete(client);
    }

    @Test
    void getAllClients_shouldReturnList() {
        when(clientRepository.findAll()).thenReturn(List.of(new Client(), new Client()));

        List<Client> result = clientService.getAllClients();

        assertEquals(2, result.size());
    }
}
