package com.example.bankapplication.dto.request.account;

import com.example.bankapplication.enums.AccountType;
import com.example.bankapplication.enums.ExemptGMF;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreateDTO {

    private Long clientId;
    private ExemptGMF exemptGMF;
    private AccountType accountType;
}
