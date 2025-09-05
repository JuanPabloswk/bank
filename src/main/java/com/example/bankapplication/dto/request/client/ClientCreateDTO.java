package com.example.bankapplication.dto.request.client;


import com.example.bankapplication.enums.IdentificationType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class  ClientCreateDTO {

    @NotBlank(message = "Names are required")
    @Size(min = 2, message = "Names must be at least 2 characters")
    private String names;
    @NotBlank(message = "Last name is required")
    @Size(min = 2, message = "Last name must be at least 2 characters")
    private String lastNames;
    private LocalDate birthDate;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    private IdentificationType identificationType;
    private String identificationNumber;

}
