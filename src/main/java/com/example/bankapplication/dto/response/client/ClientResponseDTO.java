package com.example.bankapplication.dto.response.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponseDTO {

    private String names;
    private String lastNames;
    private String email;

}
