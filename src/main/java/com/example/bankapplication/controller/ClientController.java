package com.example.bankapplication.controller;

import com.example.bankapplication.dto.request.client.ClientCreateDTO;
import com.example.bankapplication.dto.request.client.ClientUpdateDTO;
import com.example.bankapplication.dto.response.client.ClientResponseDTO;
import com.example.bankapplication.mapper.ClientMapper;
import com.example.bankapplication.model.Client;
import com.example.bankapplication.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @PostMapping("/create-client")
    public ResponseEntity<ClientResponseDTO> createClient(@Valid @RequestBody ClientCreateDTO clientCreateDTO) {
        Client savedClient = clientService.createClient(clientCreateDTO);
        ClientResponseDTO responseDTO = clientMapper.responseDTO(savedClient);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<Client> getClientId(@PathVariable Long clientId) {
        Client client = clientService.getClientId(clientId);
        return ResponseEntity.ok().body(client);
    }

    @GetMapping("/getAllClients")
    public ResponseEntity<List<Client>> getAllClients() {
        return ResponseEntity.ok().body(clientService.getAllClients());
    }

    @PutMapping("/{clientId}")
    public ResponseEntity<Client> updateClient(@PathVariable Long clientId, @RequestBody ClientUpdateDTO clientUpdateDTO) {
        Client updatedClient = clientService.updateClient(clientId, clientUpdateDTO);
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/{clientId}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long clientId) {
        clientService.deleteClient(clientId);
        return ResponseEntity.noContent().build();
    }
}
