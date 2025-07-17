package com.example.bankapplication.service.impl;

import com.example.bankapplication.exception.ClientHasAccountsException;
import com.example.bankapplication.exception.ClientUnderageException;
import com.example.bankapplication.exception.ClientNotFoundException;
import com.example.bankapplication.dto.ClientCreateDTO;
import com.example.bankapplication.dto.ClientUpdateDTO;
import com.example.bankapplication.mapper.ClientMapper;
import com.example.bankapplication.model.Client;
import com.example.bankapplication.repository.ClientRepository;
import com.example.bankapplication.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;


@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientMapper clientMapper;
    private final ClientRepository clientRepository;

    @Override
    public Client createClient(ClientCreateDTO clientCreateDTO) {
        if(Period.between(clientCreateDTO.getBirthDate(), LocalDate.now()).getYears() < 18) {
            throw new ClientUnderageException("The client is not of legal age");
        }
        Client client = clientMapper.createClient(clientCreateDTO);
        return clientRepository.save(client);
    }

    @Override
    public Client updateClient(Long clientId, ClientUpdateDTO clientUpdateDTO) {
        Client client = clientRepository.findByClientId(clientId)
                .orElseThrow(() -> new ClientNotFoundException("Client not found"));
        client.setNames(clientUpdateDTO.getNames());
        client.setLastNames(clientUpdateDTO.getLastNames());
        client.setEmail(clientUpdateDTO.getEmail());
        client.setModificationDate(LocalDateTime.now());
        return clientRepository.save(client);
    }

    @Override
    public void deleteClient(Long clientId) {
        Client client = clientRepository.findByClientId(clientId)
                .orElseThrow(() -> new ClientNotFoundException("Client not found"));
        if(client.getAccounts().isEmpty()) {
            clientRepository.delete(client);
        } else {
            throw new ClientHasAccountsException("The client cannot be deleted because it has an associated product");
        }

    }
}
