package com.example.bankapplication.mapper;

import com.example.bankapplication.dto.ClientCreateDTO;
import com.example.bankapplication.model.Client;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ClientMapper {

    public Client createClient(ClientCreateDTO clientCreateDTO) {
        return Client.builder()
                .creationDate(LocalDateTime.now())
                .names(clientCreateDTO.getNames())
                .lastNames(clientCreateDTO.getLastNames())
                .birthDate(clientCreateDTO.getBirthDate())
                .email(clientCreateDTO.getEmail())
                .identificationType(clientCreateDTO.getIdentificationType())
                .identificationNumber(clientCreateDTO.getIdentificationNumber())
                .modificationDate(LocalDateTime.now())
                .build();
    }
}
