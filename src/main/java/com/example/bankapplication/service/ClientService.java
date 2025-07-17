package com.example.bankapplication.service;

import com.example.bankapplication.dto.ClientCreateDTO;
import com.example.bankapplication.dto.ClientUpdateDTO;
import com.example.bankapplication.model.Client;

public interface ClientService {

    Client createClient(ClientCreateDTO clientCreateDTO);
    Client updateClient(Long clientId, ClientUpdateDTO clientUpdateDTO);
    void deleteClient(Long clientId);
}
