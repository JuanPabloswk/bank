package com.example.bankapplication.service.impl;

import com.example.bankapplication.dto.ClientCreateDTO;
import com.example.bankapplication.dto.ClientUpdateDTO;
import com.example.bankapplication.mapper.ClientMapper;
import com.example.bankapplication.model.Client;
import com.example.bankapplication.repository.ClientRepository;
import com.example.bankapplication.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientMapper clientMapper;
    private final ClientRepository clientRepository;

    @Override
    public String createClient(ClientCreateDTO clientCreateDTO) {
        clientRepository.save(clientMapper.createClient(clientCreateDTO));
        return "Saved successfully";
    }

    @Override
    public String updateClient(Long clientId, ClientUpdateDTO clientUpdateDTO) {
        Client client = clientRepository.findByClientId(clientId).orElseThrow(RuntimeException::new);
        client.setNames(clientUpdateDTO.getNames());
        client.setLastNames(clientUpdateDTO.getLastNames());
        client.setEmail(clientUpdateDTO.getEmail());
        client.setModificationDate(LocalDateTime.now());
        clientRepository.save(client);
        return "Updated successfully";
    }
}
