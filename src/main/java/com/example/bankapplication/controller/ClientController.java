package com.example.bankapplication.controller;

import com.example.bankapplication.dto.ClientCreateDTO;
import com.example.bankapplication.dto.ClientUpdateDTO;
import com.example.bankapplication.model.Client;
import com.example.bankapplication.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    @PostMapping("/create-client")
    public ResponseEntity<Client> createClient(@Valid @RequestBody ClientCreateDTO clientCreateDTO) {
        Client savedClient = clientService.createClient(clientCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClient);
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
