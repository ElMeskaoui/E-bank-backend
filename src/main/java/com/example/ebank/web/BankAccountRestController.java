package com.example.ebank.web;

import com.example.ebank.dtos.AccountHistoryDto;
import com.example.ebank.dtos.BankAccountDto;
import com.example.ebank.dtos.OperationDto;
import com.example.ebank.exceptions.BankAccountNotFind;
import com.example.ebank.services.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")

public class BankAccountRestController {
    private final BankAccountService bankAccountService;

    public BankAccountRestController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/bankAccounts")
    public List<BankAccountDto> listbankAccountDto(){
        return bankAccountService.listBankAccounts();
    }

    @GetMapping("/bankAccounts/{id}")
    public BankAccountDto getBankAccountDto(@PathVariable(name = "id") String bankAccountId) throws BankAccountNotFind {
        return bankAccountService.getBankAccount(bankAccountId);
    }
    
    @GetMapping("/bankAccounts/{id}/operation")
    public List<OperationDto> getOperation(@PathVariable(name = "id") String accountId){
        return bankAccountService.accountHistory(accountId);
    }

    @GetMapping("/bankAccounts/{id}/pageOperation")
    public AccountHistoryDto getAccountHistory(@PathVariable(name = "id") String accountId,
                                               @RequestParam(name = "page",defaultValue = "0") int page,
                                               @RequestParam(name = "size",defaultValue = "5") int size) throws BankAccountNotFind {
        return  bankAccountService.getAccountHistory(accountId,page,size);
    }
}
