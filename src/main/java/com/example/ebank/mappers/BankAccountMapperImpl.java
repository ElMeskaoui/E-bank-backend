package com.example.ebank.mappers;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.example.ebank.dtos.CurrentBankAccountDto;
import com.example.ebank.dtos.CustomerDto;
import com.example.ebank.dtos.OperationDto;
import com.example.ebank.dtos.SavingBankAccountDto;
import com.example.ebank.entities.CurrentAccount;
import com.example.ebank.entities.Customer;
import com.example.ebank.entities.Operation;
import com.example.ebank.entities.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapperImpl {

    public CustomerDto fromCustomer(Customer customer){
        CustomerDto customerDto=new CustomerDto();
        BeanUtils.copyProperties(customer,customerDto);
        return customerDto;
    }
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    public Customer fromCustomerDto(CustomerDto customerDto){
        Customer customer=new Customer();
        String pw = passwordEncoder().encode(customerDto.getPassword());
        BeanUtils.copyProperties(customerDto,customer);
        customer.setPassword(pw);
        return customer;
    }

    public SavingBankAccountDto fromSavingAccount(SavingAccount savingAccount){
        SavingBankAccountDto savingBankAccountDto=new SavingBankAccountDto();
        BeanUtils.copyProperties(savingAccount,savingBankAccountDto);
        savingBankAccountDto.setCustomerDto(fromCustomer(savingAccount.getCustomer()));
        savingBankAccountDto.setType(savingAccount.getClass().getSimpleName());
        return savingBankAccountDto;
    }
    public SavingAccount fromSavingBankAccountDto(SavingBankAccountDto savingBankAccountDto){
        SavingAccount savingAccount=new SavingAccount();
        BeanUtils.copyProperties(savingBankAccountDto, savingAccount);
        savingAccount.setCustomer(fromCustomerDto(savingBankAccountDto.getCustomerDto()));
        return savingAccount;
    }
    public CurrentBankAccountDto fromCurrentAccount(CurrentAccount currentAccount){
        CurrentBankAccountDto currentBankAccountDto=new CurrentBankAccountDto();
        BeanUtils.copyProperties(currentAccount,currentBankAccountDto);
        currentBankAccountDto.setCustomerDto(fromCustomer(currentAccount.getCustomer()));
        currentBankAccountDto.setType(currentAccount.getClass().getSimpleName());
        return currentBankAccountDto;
    }
    public CurrentAccount fromCurrentBankAccountDto(CurrentBankAccountDto currentBankAccountDto){
        CurrentAccount currentAccount=new CurrentAccount();
        BeanUtils.copyProperties(currentBankAccountDto, currentAccount);
        currentAccount.setCustomer(fromCustomerDto(currentBankAccountDto.getCustomerDto()));
        return currentAccount;
    }

    public OperationDto fromOperation(Operation operation){
        OperationDto operationDto=new OperationDto();
        BeanUtils.copyProperties(operation,operationDto);
        return operationDto;
    }
    public Operation fromOperationDto(OperationDto operationDto){
        Operation operation=new Operation();
        BeanUtils.copyProperties(operationDto,operation);
        return operation;
    }
}
