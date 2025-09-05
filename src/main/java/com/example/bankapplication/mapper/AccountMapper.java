package com.example.bankapplication.mapper;

import com.example.bankapplication.dto.request.account.AccountCreateDTO;
import com.example.bankapplication.dto.response.account.AccountResponseDTO;
import com.example.bankapplication.enums.AccountStatus;
import com.example.bankapplication.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class, AccountStatus.class})
public interface AccountMapper {

    @Mapping(target = "accountNumber", source = "uniqueAccountNumber")
    @Mapping(target = "accountStatus", expression = "java(AccountStatus.ACTIVE)")
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "updatedAt", ignore = true)
    Account createAccount(AccountCreateDTO accountCreateDTO, String uniqueAccountNumber);

    AccountResponseDTO responseDTO(Account account);
}

