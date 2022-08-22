package com.example.ebank.web;

import com.example.ebank.dtos.AddOperationDto;
import com.example.ebank.dtos.OperationDto;
import com.example.ebank.dtos.TransferOperationDto;
import com.example.ebank.exceptions.BalanceNotSufficientException;
import com.example.ebank.exceptions.BankAccountNotFind;
import com.example.ebank.services.BankAccountService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OperationRestController {
    private BankAccountService bankAccountService;

    public OperationRestController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }
    @PostMapping("/bankAccounts/{id}/operation/credit")
    public void CreditToAccount(@PathVariable(name = "id") String accountId,@RequestBody OperationDto operationDto) throws BankAccountNotFind, BalanceNotSufficientException {
        bankAccountService.credit(accountId,operationDto.getAmount(),operationDto.getDescription());

    }
    @PostMapping("/bankAccounts/{id}/operation/debit")
    public void debitToAccount(@PathVariable(name = "id") String accountId,@RequestBody AddOperationDto operationDto) throws BankAccountNotFind, BalanceNotSufficientException {
        bankAccountService.debit(accountId, operationDto.getAmount(), operationDto.getDescription());
    }
    @PostMapping("/bankAccounts/{id}/operation/transfer")
    public void transferToAccount(@PathVariable(name = "id") String accountSourceId,@RequestBody TransferOperationDto transferOperationDto) throws BankAccountNotFind, BalanceNotSufficientException {
        bankAccountService.transfer(accountSourceId,transferOperationDto.getId(),transferOperationDto.getAmount());
    }
}
