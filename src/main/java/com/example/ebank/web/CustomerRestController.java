package com.example.ebank.web;

import com.example.ebank.dtos.CustomerDto;
import com.example.ebank.entities.Customer;
import com.example.ebank.exceptions.CustomerNotFindException;
import com.example.ebank.services.BankAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class CustomerRestController {
    private BankAccountService bankAccountService;
    @GetMapping("/customers")
    public List<CustomerDto> customers(){
        return bankAccountService.listCustomers();
    }
    @GetMapping("/customers/{id}")
    public CustomerDto getcustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFindException {
        return bankAccountService.getCustomerById(customerId);
    }
    @PostMapping("/customers")
    public  CustomerDto saveCustomer(@RequestBody CustomerDto customerDto){
        return bankAccountService.saveCustomer(customerDto);
    }

    @PutMapping("/customers/{customerId}")
    public CustomerDto updateCutomer(@PathVariable Long customerId,@RequestBody CustomerDto customerDto){
        customerDto.setId(customerId);
        return bankAccountService.updateCustomer(customerDto);
    }

    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable Long id){
        bankAccountService.deleteCustomer(id);
    }
}
