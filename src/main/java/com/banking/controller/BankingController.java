package com.banking.controller;

import com.banking.entity.Account;
import com.banking.entity.Customer;
import com.banking.repository.AccountRepository;
import com.banking.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/get")
public class BankingController {

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private AccountRepository accountRepo;

    @GetMapping("/account")
    public ResponseEntity<Account> getCustomer(@RequestParam("accountNum") Long account) {
        Account response = accountRepo.findByAccountNum(account);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all-cust-accts")
    public ResponseEntity<List<Customer>> getAllCustomerAccts() {
        List<Customer> custAccts = customerRepo.findAll();
        return ResponseEntity.ok(custAccts);
    }

    @GetMapping("/cust-accounts")
    public ResponseEntity<List<Customer>> getCustomerAcct(@RequestParam("customerName") String customerName) {
        List<Customer> custAccts = customerRepo.findByCustName(customerName);
        return ResponseEntity.ok(custAccts);
    }

    @PostMapping("/account")
    public ResponseEntity<Customer> createCustAcct(@RequestBody @Validated Customer customer) {
        customer.getAccounts().get(0).setAccountNum(generateUUIDNo());
        Customer response = customerRepo.save(customer);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/account")
    public ResponseEntity<Customer> updateCustAcct(@RequestBody @Validated Customer customer) {
        Customer response = customerRepo.save(customer);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/account")
    public ResponseEntity<Account> deleteCustAcct(@RequestParam("accountNum") Long accountNum) {
        Account delAcct = null;
        List<Customer> customerList = customerRepo.findAll();
        for (Customer cust : customerList) {
            for(Account acct : cust.getAccounts()) {
                if(accountNum.equals(acct.getAccountNum())) {
//                    delAcct = acct;
                    accountRepo.delete(acct);
                    return ResponseEntity.ok(acct);
                }
            }
        }
        return ResponseEntity.ok(delAcct);
    }

    public Long generateUUIDNo() {
        return ThreadLocalRandom.current().nextLong(10000000000L, 99999999999L);
    }
}
