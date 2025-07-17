package com.example.bankapplication.service;

import com.example.bankapplication.dto.ClientCreateDTO;
import com.example.bankapplication.dto.ClientUpdateDTO;
import com.example.bankapplication.model.Client;

public interface ClientService {

    String createClient(ClientCreateDTO clientCreateDTO);
    String updateClient(Long clientId, ClientUpdateDTO clientUpdateDTO);
}
