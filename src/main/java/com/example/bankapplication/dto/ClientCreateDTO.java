package com.example.bankapplication.dto;

import com.example.bankapplication.enums.IdentificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientCreateDTO {

    private String names;
    private String lastNames;
    private LocalDate birthDate;

    private String email;

    private IdentificationType identificationType;
    private String identificationNumber;
}
