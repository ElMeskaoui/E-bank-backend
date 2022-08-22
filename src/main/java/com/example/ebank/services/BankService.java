package com.example.ebank.services;

import com.example.ebank.dtos.*;
import com.example.ebank.entities.BankAccount;
import com.example.ebank.entities.CurrentAccount;
import com.example.ebank.entities.Customer;
import com.example.ebank.entities.SavingAccount;
import com.example.ebank.exceptions.BalanceNotSufficientException;
import com.example.ebank.exceptions.BankAccountNotFind;
import com.example.ebank.exceptions.CustomerNotFindException;

import java.lang.reflect.Type;
import java.util.List;

public interface BankService {
    CustomerDto saveCustomer(CustomerDto customerDto);

    CustomerDto updateCustomer(CustomerDto customerDto);

    CustomerDto getCustomerById(Long id) throws CustomerNotFindException;

    void deleteCustomer(Long customerId);

    CurrentBankAccountDto saveCurrentBankAccount(double balanceInitial, double overDraft , Long customerId ) throws CustomerNotFindException;
    SavingBankAccountDto saveSavingBankAccount(double balanceInitial, double interestRate, Long customerId ) throws CustomerNotFindException;
    List<CustomerDto> listCustomers();

    List<BankAccountDto> listBankAccounts();

    BankAccountDto getBankAccount(String bankAccountId) throws BankAccountNotFind;
    void debit(String BankAccountId,double amount, String description) throws BankAccountNotFind, BalanceNotSufficientException;
    void credit(String BankAccountId,double amount, String description) throws BankAccountNotFind;
    void transfer(String bankAccountSource, String BankAccountDestination, double amount) throws BankAccountNotFind, BalanceNotSufficientException;

    List<OperationDto> accountHistory(String accountId);

    AccountHistoryDto getAccountHistory(String accountId, int page, int size) throws BankAccountNotFind;
}
