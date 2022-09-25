package com.example.ebank.services;

import com.example.ebank.dtos.*;
import com.example.ebank.entities.*;
import com.example.ebank.enums.OperationType;
import com.example.ebank.exceptions.BalanceNotSufficientException;
import com.example.ebank.exceptions.BankAccountNotFind;
import com.example.ebank.exceptions.CustomerNotFindException;
import com.example.ebank.mappers.BankAccountMapperImpl;
import com.example.ebank.repositories.BankAccountRepository;
import com.example.ebank.repositories.CustomerRepository;
import com.example.ebank.repositories.OperationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.cert.Extension;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountService implements BankService{
    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private OperationRepository operationRepository;
    private BankAccountMapperImpl bankAccountMapper;

    @Override
    public CustomerDto saveCustomer(CustomerDto customerDto) {
//        log.info("Saving new Customer");
        Customer customer=bankAccountMapper.fromCustomerDto(customerDto);
        Customer savedCustomer=customerRepository.save(customer);
        return bankAccountMapper.fromCustomer(savedCustomer);
    }
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



    @Override
    public CustomerDto updateCustomer(CustomerDto customerDto) {
        log.info("Saving update Customer");
        Customer customer=bankAccountMapper.fromCustomerDto(customerDto);
        Customer savedCustomer=customerRepository.save(customer);
        return bankAccountMapper.fromCustomer(savedCustomer);
    }
    @Override
    public CustomerDto getCustomerById(Long id) throws CustomerNotFindException {
        Customer customer=customerRepository.findById(id).orElseThrow(()->new CustomerNotFindException("Customer Not Find "));
        return bankAccountMapper.fromCustomer(customer);
    }

    @Override
    public void deleteCustomer(Long customerId){
        customerRepository.deleteById(customerId);
    }

    @Override
    public CurrentBankAccountDto saveCurrentBankAccount(double balanceInitial, double overDraft, Long customerId) throws CustomerNotFindException {
        CurrentAccount currentAccount=new CurrentAccount();
        Customer customer=customerRepository.findById(customerId).orElseGet(null);
        if (customer==null){
            throw new CustomerNotFindException ("Customer not find");
        }
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreatedAt(new Date());
        currentAccount.setBalance(balanceInitial);
        currentAccount.setCustomer(customer);
        currentAccount.setOverDraft(overDraft);
        CurrentAccount savedCurrentAccount=bankAccountRepository.save(currentAccount);
        return bankAccountMapper.fromCurrentAccount(savedCurrentAccount);
    }

    @Override
    public SavingBankAccountDto saveSavingBankAccount(double balanceInitial, double interestRate, Long customerId) throws CustomerNotFindException {
        SavingAccount savingAccount=new SavingAccount();
        Customer customer=customerRepository.findById(customerId).orElseGet(null);
        if (customer==null){
            throw new CustomerNotFindException ("Customer not find");
        }
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreatedAt(new Date());
        savingAccount.setBalance(balanceInitial);
        savingAccount.setCustomer(customer);
        savingAccount.setInterestRate(interestRate);
        SavingAccount savedSavingAccount=bankAccountRepository.save(savingAccount);
        return bankAccountMapper.fromSavingAccount(savedSavingAccount);
    }



    @Override
    public List<CustomerDto> listCustomers() {
        List<Customer> customers=customerRepository.findAll();
        List<CustomerDto> customerDtos = customers.stream()
                .map(customer -> bankAccountMapper.fromCustomer(customer))
                .collect(Collectors.toList());
//        List<CustomerDto> customerDtos=new ArrayList<>();
//        for (Customer customer:customers){
//            CustomerDto customerDto=bankAccountMapper.fromCustomer(customer);
//            customerDtos.add(customerDto);
//        }
        return customerDtos;
    }

    @Override
    public List<BankAccountDto> listBankAccounts(){
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        List<BankAccountDto> bankAccountDtos = bankAccounts.stream().map(bankAccount -> {
            if (bankAccount instanceof SavingAccount) {
                SavingAccount savingAccount = (SavingAccount) bankAccount;
                return bankAccountMapper.fromSavingAccount(savingAccount);
            } else {
                CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                return bankAccountMapper.fromCurrentAccount(currentAccount);
            }
        }).collect(Collectors.toList());
        return bankAccountDtos;
    }

    @Override
    public BankAccountDto getBankAccount(String bankAccountId) throws BankAccountNotFind {
        BankAccount bankAccount=bankAccountRepository.findById(bankAccountId)
                .orElseThrow(()->new BankAccountNotFind("Bank Account Not Find"));
        if (bankAccount instanceof SavingAccount){
            SavingAccount savingAccount=(SavingAccount) bankAccount;
            return bankAccountMapper.fromSavingAccount(savingAccount);
        } else {
            CurrentAccount currentAccount=(CurrentAccount) bankAccount;
            return bankAccountMapper.fromCurrentAccount(currentAccount);
        }
    }

    @Override
    public void debit(String BankAccountId, double amount, String description) throws BankAccountNotFind, BalanceNotSufficientException {
        BankAccount bankAccount=bankAccountRepository.findById(BankAccountId)
                .orElseThrow(()->new BankAccountNotFind("Bank Account Not Find"));
        if (bankAccount.getBalance()<amount){
            throw new BalanceNotSufficientException("Balance Not Sufficent");
        }
        Operation operation=new Operation();
        operation.setOperationDate(new Date());
        operation.setAmount(amount);
        operation.setType(OperationType.DEBIT);
        operation.setDescription(description);
        operation.setBankAccount(bankAccount);
        operationRepository.save(operation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(String BankAccountId, double amount, String description) throws BankAccountNotFind {
        BankAccount bankAccount=bankAccountRepository.findById(BankAccountId)
                .orElseThrow(()->new BankAccountNotFind("Bank Account Not Find"));
        Operation operation=new Operation();
        operation.setOperationDate(new Date());
        operation.setAmount(amount);
        operation.setType(OperationType.CREDIT);
        operation.setBankAccount(bankAccount);
        operation.setDescription(description);
        operationRepository.save(operation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfer(String bankAccountSource, String BankAccountDestination, double amount) throws BankAccountNotFind, BalanceNotSufficientException {
        debit(bankAccountSource,amount,"transfer to "+BankAccountDestination);
        credit(BankAccountDestination,amount,"transfer from "+ bankAccountSource);
    }
    @Override
    public List<OperationDto> accountHistory(String accountId){
        List<Operation> operationList = operationRepository.findByBankAccountId(accountId);
        return operationList.stream().map(operation -> bankAccountMapper.fromOperation(operation))
                .collect(Collectors.toList());
    }

    @Override
    public AccountHistoryDto getAccountHistory(String accountId, int page, int size) throws BankAccountNotFind {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(()->new BankAccountNotFind("Bank Account Not Find"));
        Page<Operation> accountOperation = operationRepository.findByBankAccountIdOrderByOperationDateDesc(accountId, PageRequest.of(page, size));
        AccountHistoryDto accountHistoryDto=new AccountHistoryDto();
        List<OperationDto> collect = accountOperation.getContent().stream().map(op ->bankAccountMapper.fromOperation(op)).collect(Collectors.toList());
        accountHistoryDto.setOperationDtoList(collect);
        accountHistoryDto.setAccountId(bankAccount.getId());
        accountHistoryDto.setBalance(bankAccount.getBalance());
        accountHistoryDto.setCurrency(bankAccount.getCurrency());
        accountHistoryDto.setPageSize(size);
        accountHistoryDto.setTotalPages(accountOperation.getTotalPages());
        accountHistoryDto.setCurrentPage(page);
        return accountHistoryDto;
    }

    @Override
    public List<CustomerDto> searchCustomer(String keyword) {
        List<Customer> customers=customerRepository.searchCustomer(keyword);
        List<CustomerDto> customerDtoList = customers.stream().map(customer -> bankAccountMapper.fromCustomer(customer)).collect(Collectors.toList());
        return customerDtoList;
    }
}
