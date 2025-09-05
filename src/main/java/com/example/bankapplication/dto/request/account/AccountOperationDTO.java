package com.example.bankapplication.dto.request.account;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountOperationDTO {

    private double amount;
    private String accountNumber;
}
