package com.example.ebank.web;

import com.example.ebank.dtos.*;
import com.example.ebank.exceptions.BalanceNotSufficientException;
import com.example.ebank.exceptions.BankAccountNotFind;
import com.example.ebank.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
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
    @PostMapping("/account/debit")
    public DebitDto debit(@RequestBody DebitDto debitDto) throws BankAccountNotFind, BalanceNotSufficientException {
        this.bankAccountService.debit(debitDto.getAccountId(), debitDto.getAmount(), debitDto.getDescription());
        return debitDto;
    }
    @PostMapping("/account/credit")
    public CreditDto credit(@RequestBody CreditDto creditDto) throws BankAccountNotFind, BalanceNotSufficientException {
        this.bankAccountService.credit(creditDto.getAccountId(), creditDto.getAmount(), creditDto.getDescription());
        return creditDto;
    }
    @PostMapping("/account/transfer")
    public void  transfer(@RequestBody TransferDto transferDto) throws BankAccountNotFind, BalanceNotSufficientException {
        this.bankAccountService.transfer(transferDto.getAccountSource(),transferDto.getAccountDestination() , transferDto.getAmount());
    }
}