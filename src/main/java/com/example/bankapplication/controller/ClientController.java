package com.example.bankapplication.controller;

import com.example.bankapplication.dto.ClientCreateDTO;
import com.example.bankapplication.dto.ClientUpdateDTO;
import com.example.bankapplication.service.impl.ClientServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientServiceImpl clientServiceImpl;

    @PostMapping("/create-client")
    public String createClient(@RequestBody ClientCreateDTO clientCreateDTO) {
        return clientServiceImpl.createClient(clientCreateDTO);
    }

    @PutMapping("/{clientId}")
    public ResponseEntity<String> updateClient(@PathVariable Long clientId, @RequestBody ClientUpdateDTO clientUpdateDTO) {
        return ResponseEntity.ok().body(clientServiceImpl.updateClient(clientId, clientUpdateDTO));
    }
}
