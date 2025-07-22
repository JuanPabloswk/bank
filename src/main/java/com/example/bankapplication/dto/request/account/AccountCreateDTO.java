package com.example.bankapplication.dto.request.account;

import com.example.bankapplication.enums.AccountType;
import com.example.bankapplication.enums.ExemptGMF;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountCreateDTO {

    private ExemptGMF exemptGMF;
    private AccountType accountType;
}
