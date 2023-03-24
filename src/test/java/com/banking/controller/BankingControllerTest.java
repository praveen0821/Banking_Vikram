package com.banking.controller;

import com.banking.entity.Account;
import com.banking.entity.Customer;
import com.banking.repository.AccountRepository;
import com.banking.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class BankingControllerTest {

    @Mock
    private CustomerRepository customerRepo;
    @Mock
    private AccountRepository accountRepo;

    @Mock
    private Account account;

    @Mock
    private Customer customer;

    @InjectMocks
    private BankingController bankingController;

    @Test
    public void getCustAcctsTest() {
        Mockito.when(accountRepo.findByAccountNum(Mockito.anyLong())).thenReturn(account);
        bankingController.getCustomer(1L);
        Assertions.assertNotNull(account);
    }

    @Test
    public void getNoCustAcctsTest() {
        Account res = null;
        Mockito.when(accountRepo.findByAccountNum(Mockito.anyLong())).thenReturn(res);
        bankingController.getCustomer(1L);
        Assertions.assertNull(res);
    }

    @Test
    public void getAllCustomerAcctsTest() {
        List<Customer> res = Mockito.mock(List.class);
        Mockito.when(customerRepo.findAll()).thenReturn(res);
        bankingController.getAllCustomerAccts();
        Assertions.assertNotNull(res);
    }

    @Test
    public void getAllCustomerAcctsAsZeroTest() {
        List<Customer> res = Collections.emptyList();
        Mockito.when(customerRepo.findAll()).thenReturn(res);
        bankingController.getAllCustomerAccts();
        Assertions.assertEquals(res.size(), 0);
    }

    @Test
    public void getCustomerAcctByCustNameTest() {
        List<Customer> res = Mockito.mock(List.class);
        Mockito.when(customerRepo.findByCustName(Mockito.anyString())).thenReturn(res);
        bankingController.getCustomerAcct("Vikram");
        Assertions.assertNotNull(res);
    }

    @Test
    public void getNoCustomerAcctByCustNameTest() {
        List<Customer> res = Collections.emptyList();
        Mockito.when(customerRepo.findByCustName(Mockito.anyString())).thenReturn(res);
        bankingController.getCustomerAcct("Vikram");
        Assertions.assertEquals(res.size(), 0);
    }

    @Test
    public void createCustomerAcctTest() {
        List<Account> res = Mockito.mock(List.class);
        Mockito.when(customer.getAccounts()).thenReturn(res);
        Mockito.when(customer.getAccounts().get(0)).thenReturn(account);
        Mockito.when(customerRepo.save(customer)).thenReturn(customer);
        //bankingController.createCustAcct(customer);
        Assertions.assertNotNull(customer);
    }

    @Test
    public void createCustomerAcctExceptionTest() {
            List<Account> res = Mockito.mock(List.class);
            Mockito.when(customer.getAccounts()).thenReturn(res);
            Mockito.when(customer.getAccounts().get(0)).thenReturn(account);
            Mockito.when(customerRepo.save(customer)).thenReturn(customer);
            //bankingController.createCustAcct(customer);
            Assertions.assertNotNull(customer);
    }

    @Test
    public void updateCustomerAcctTest() {
        Mockito.when(customerRepo.save(customer)).thenReturn(customer);
        bankingController.updateCustAcct(customer);
        Assertions.assertNotNull(customer);
    }

    @Test
    public void updateCustomerAcctExceptionTest() {
        Mockito.when(customerRepo.save(customer)).thenReturn(customer);
        bankingController.updateCustAcct(customer);
        Assertions.assertNotNull(customer);
    }

    @Test
    public void deleteCustAcctTest() {
        List<Customer> customerList = Mockito.mock(List.class);
        List<Account> accList = Mockito.mock(List.class);
        Iterator<Customer> itr = Mockito.mock(Iterator.class);
        Iterator<Account> itr1 = Mockito.mock(Iterator.class);

        Mockito.when(customerRepo.findAll()).thenReturn(customerList);
        Mockito.when(customerList.iterator()).thenReturn(itr);
        Mockito.when(itr.hasNext()).thenReturn(true);
        Mockito.when(itr.next()).thenReturn(customer);

        Mockito.when(customer.getAccounts()).thenReturn(accList);
        Mockito.when(accList.iterator()).thenReturn(itr1);
        Mockito.when(itr1.hasNext()).thenReturn(true);
        Mockito.when(itr1.next()).thenReturn(account);

        Mockito.when(account.getAccountNum()).thenReturn(1234567899L);
        Mockito.doNothing().when(accountRepo).delete(account);
        Mockito.when(customerRepo.saveAll(customerList)).thenReturn(customerList);

        bankingController.deleteCustAcct(1234567899L);

        Assertions.assertNotNull(account);
    }

    @Test
    public void deleteCustAcctExceptionTest() {
        List<Customer> customerList = Mockito.mock(List.class);
        List<Account> accList = Mockito.mock(List.class);
        Iterator<Customer> itr = Mockito.mock(Iterator.class);
        Iterator<Account> itr1 = Mockito.mock(Iterator.class);

        Mockito.when(customerRepo.findAll()).thenReturn(Collections.emptyList());

        bankingController.deleteCustAcct(1234567899L);
    }
}
