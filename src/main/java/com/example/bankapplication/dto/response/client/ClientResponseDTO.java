package com.example.bankapplication.dto.response.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientResponseDTO {

    private String names;
    private String lastNames;
    private LocalDate birthDate;
}
