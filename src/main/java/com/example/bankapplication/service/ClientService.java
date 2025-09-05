package com.example.bankapplication.service;

import com.example.bankapplication.dto.request.client.ClientCreateDTO;
import com.example.bankapplication.dto.request.client.ClientUpdateDTO;
import com.example.bankapplication.model.Client;

import java.util.List;

public interface ClientService {

    Client createClient(ClientCreateDTO clientCreateDTO);
    Client getClientId(Long clientId);
    List<Client> getAllClients();
    Client updateClient(Long clientId, ClientUpdateDTO clientUpdateDTO);
    void deleteClient(Long clientId);
}
