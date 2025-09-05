package com.example.bankapplication.mapper;

import com.example.bankapplication.dto.request.client.ClientCreateDTO;
import com.example.bankapplication.dto.response.client.ClientResponseDTO;
import com.example.bankapplication.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class})
public interface ClientMapper {

    @Mapping(target = "clientId", ignore = true)
    @Mapping(target = "creationDate", expression = "java(LocalDateTime.now())")
    @Mapping(target = "modificationDate", ignore = true)
    @Mapping(target = "accounts", ignore = true)
    Client createClient(ClientCreateDTO clientCreateDTO);

    ClientResponseDTO responseDTO(Client client);
}
