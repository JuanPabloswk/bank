package com.example.bankapplication.mapper;

import com.example.bankapplication.dto.request.transaction.TransactionCreateDTO;
import com.example.bankapplication.enums.OperationType;
import com.example.bankapplication.model.Account;
import com.example.bankapplication.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class, OperationType.class})
public interface TransactionMapper {

    @Mapping(target = "operationType", expression = "java(OperationType.TRANSFER)")
    @Mapping(target = "transactionDate", expression = "java(LocalDateTime.now())")
    @Mapping(target = "sourceAccount", source = "source")
    @Mapping(target = "destinationAccount", source = "destination")

    Transaction makeTransfer(TransactionCreateDTO dto, Account source, Account destination);
}


