package com.example.ebank;

import com.example.ebank.dtos.CurrentBankAccountDto;
import com.example.ebank.dtos.CustomerDto;
import com.example.ebank.dtos.SavingBankAccountDto;
import com.example.ebank.entities.*;
import com.example.ebank.enums.AccountStatus;
import com.example.ebank.enums.OperationType;
import com.example.ebank.exceptions.BalanceNotSufficientException;
import com.example.ebank.exceptions.BankAccountNotFind;
import com.example.ebank.exceptions.CustomerNotFindException;
import com.example.ebank.repositories.BankAccountRepository;
import com.example.ebank.repositories.CustomerRepository;
import com.example.ebank.repositories.OperationRepository;
import com.example.ebank.repositories.RoleRepository;
import com.example.ebank.services.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EBankApplication implements CommandLineRunner{

    public static void main(String[] args) {
        SpringApplication.run(EBankApplication.class, args);
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CustomerRepository customerRepository;




//    @Bean
//    CommandLineRunner start(BankAccountService bankAccountService){
//
//        return args -> {
//            Stream.of("Hassan","Imane","koutare").forEach(name->{
//                CustomerDto customer=new CustomerDto();
//                customer.setName(name);
//                customer.setEmail(name+"@gmail.com");
//                bankAccountService.saveCustomer(customer);
//            });
//            bankAccountService.listCustomers().forEach(customer -> {
//                try {
//                    bankAccountService.saveCurrentBankAccount(Math.random()*255,9000, customer.getId());
//                    bankAccountService.saveSavingBankAccount(Math.random()*255,5, customer.getId());
//                } catch (CustomerNotFindException e) {
//                    e.printStackTrace();
//                }
//            });
//            bankAccountService.listBankAccounts().forEach(bankAccount -> {
//                for (int i=0;i<20;i++){
//                    String id;
//                    if (bankAccount instanceof SavingBankAccountDto) {
//                        SavingBankAccountDto savingBankAccountDto = (SavingBankAccountDto) bankAccount;
//                        id=savingBankAccountDto.getId();
//                    }else {
//                        CurrentBankAccountDto currentBankAccountDto = (CurrentBankAccountDto) bankAccount;
//                        id=currentBankAccountDto.getId();
//                    }
//                    try {
//                        bankAccountService.credit(id, 10000+Math.random()*199215,"operation credit to "+id);
//                        bankAccountService.debit(id, 100+Math.random()*132,"operation debit to "+id);
//
//                    } catch (BankAccountNotFind | BalanceNotSufficientException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        };
//    }


    //    @Bean
//    CommandLineRunner commandLineRunner(CustomerRepository customerRepository, BankAccountRepository bankAccountRepository, OperationRepository operationRepository){
//        return args -> {
//            Stream.of("elhoucine","Ali","mohammed").forEach(name->{
//                Customer customer=new Customer();
//                customer.setName(name);
//                customer.setEmail(name+"@gmail.com");
//                customerRepository.save(customer);
//            });
//            customerRepository.findAll().forEach(customer -> {
//                CurrentAccount currentAccount=new CurrentAccount();
//                currentAccount.setId(UUID.randomUUID().toString());
//                currentAccount.setCreatedAt(new Date());
//                currentAccount.setBalance(Math.random()*9000);
//                currentAccount.setStatus(AccountStatus.CREATED);
//                currentAccount.setCurrency("HD");
//                currentAccount.setOverDraft(9000);
//                currentAccount.setCustomer(customer);
//                bankAccountRepository.save(currentAccount);
//
//                SavingAccount savingAccount=new SavingAccount();
//                savingAccount.setId(UUID.randomUUID().toString());
//                savingAccount.setCreatedAt(new Date());
//                savingAccount.setBalance(Math.random()*9000);
//                savingAccount.setStatus(AccountStatus.CREATED);
//                savingAccount.setCurrency("HD");
//                savingAccount.setInterestRate(5);
//                savingAccount.setCustomer(customer);
//                bankAccountRepository.save(savingAccount);
//            });
//            bankAccountRepository.findAll().forEach(bankAccount -> {
//                for (int i=0;i<5;i++){
//                    Operation operation=new Operation();
//                    operation.setOperationDate(new Date());
//                    operation.setAmount(Math.random()*5);
//                    operation.setType(Math.random()>0.5?OperationType.CREDIT:OperationType.DEBIT);
//                    operation.setBankAccount(bankAccount);
//                    operationRepository.save(operation);
//                }
//            });
//        };
//    }


    @Override
    public void run(String... args) throws Exception {
        Role a = roleRepository.save(new Role(1L,"admin"));
        Role c = roleRepository.save(new Role(2L,"Customer"));

        List<Role> customerRole = new ArrayList<>();
        List<Role> adminRole = new ArrayList<>();
        customerRole.add(c);
        adminRole.add(a);
        customerRepository.save(new Customer(1,"elhoucine","elhouicne@gmail.com",passwordEncoder().encode("12345"),adminRole,null));
        customerRepository.save(new Customer(2,"mohmmed","mkolich@gmail.com",passwordEncoder().encode("87654"),customerRole,null));
        customerRepository.save(new Customer(3,"ali","ali@gmail.com",passwordEncoder().encode("123"),customerRole,null));

    }
}
