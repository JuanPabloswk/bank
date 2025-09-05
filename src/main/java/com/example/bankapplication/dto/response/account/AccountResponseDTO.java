package com.example.bankapplication.dto.response.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponseDTO {

    private double balance;
    private String accountNumber;

}
