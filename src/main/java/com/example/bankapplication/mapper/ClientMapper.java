package com.example.bankapplication.mapper;

import com.example.bankapplication.dto.request.client.ClientCreateDTO;
import com.example.bankapplication.dto.response.client.ClientResponseDTO;
import com.example.bankapplication.model.Client;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ClientMapper {

    public Client createClient(ClientCreateDTO clientCreateDTO) {
        Client client = new Client();
        client.setCreationDate(LocalDateTime.now());
        client.setNames(clientCreateDTO.getNames());
        client.setLastNames(clientCreateDTO.getLastNames());
        client.setBirthDate(clientCreateDTO.getBirthDate());
        client.setEmail(clientCreateDTO.getEmail());
        client.setIdentificationType(clientCreateDTO.getIdentificationType());
        client.setIdentificationNumber(clientCreateDTO.getIdentificationNumber());
        client.setModificationDate(LocalDateTime.now());
        return client;
    }

    public ClientResponseDTO responseDTO(Client client) {
        ClientResponseDTO clientResponseDTO = new ClientResponseDTO();
        clientResponseDTO.setNames(client.getNames());
        clientResponseDTO.setLastNames(client.getLastNames());
        clientResponseDTO.setEmail(client.getEmail());
        return clientResponseDTO;
    }

}
