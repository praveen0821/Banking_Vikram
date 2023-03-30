package com.banking.controller;

import com.banking.entity.Account;
import com.banking.entity.Customer;
import com.banking.exception.BadRequestException;
import com.banking.repository.AccountRepository;
import com.banking.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@ExtendWith(MockitoExtension.class)
@RestClientTest({BankingController.class, CustomerRepository.class, AccountRepository.class})
public class BankingControllerRestClientTest {
    @Autowired
    private MockRestServiceServer mockServer;

    @Autowired
    private BankingController controller;

    private static final String baseUrl = "http://localhost:8080/banking";

    @Test
    public void depositAmountTest() throws BadRequestException {
        Account acc = new Account();
        acc.setAccountNum(1010101010L);
        acc.setBalanceAmt(1000.0d);
        acc.setAcctId(2L);
        this.mockServer.expect(requestTo(baseUrl + "/deposit"))
                .andRespond(withSuccess((Resource) acc, MediaType.APPLICATION_JSON));

        ResponseEntity<Account> res = controller.custAcctDeposit(1000.0d, 1010101010L);
        assertEquals(1000.0d, res.getBody().getBalanceAmt());
        assertEquals(2L, res.getBody().getAcctId());
    }

    @Test
    public void depositAmountLimitExceptionTest() throws BadRequestException {
        Account acc = new Account();
        acc.setAccountNum(1010101010L);
        acc.setBalanceAmt(1000.0d);
        acc.setAcctId(2L);
        this.mockServer.expect(requestTo(baseUrl + "/deposit"))
                .andRespond(withStatus(HttpStatus.BAD_REQUEST));

        assertThrows(BadRequestException.class, () -> controller.custAcctDeposit(10010.0d, 1010101010L));
    }

    @Test
    public void withdrawAmountTest() throws BadRequestException {
        Account acc = new Account();
        acc.setAccountNum(1010101010L);
        acc.setBalanceAmt(1000.0d);
        acc.setAcctId(2L);
        this.mockServer.expect(requestTo(baseUrl + "/withdraw"))
                .andRespond(withSuccess((Resource) acc, MediaType.APPLICATION_JSON));

        ResponseEntity<Account> res = controller.custAcctWithdraw(200.0d, 1010101010L);
        assertEquals(800.0d, res.getBody().getBalanceAmt());
        assertEquals(2L, res.getBody().getAcctId());
    }

    @Test
    public void withdrawAmountLimitExceptionTest() throws BadRequestException {
        Account acc = new Account();
        acc.setAccountNum(1010101010L);
        acc.setBalanceAmt(1000.0d);
        acc.setAcctId(2L);
        this.mockServer.expect(requestTo(baseUrl + "/withdraw"))
                .andRespond(withStatus(HttpStatus.BAD_REQUEST));

        assertThrows(BadRequestException.class, () -> controller.custAcctWithdraw(950.0d, 1010101010L));
    }

    @Test
    public void withdrawAmountMoreThan90PercentExceptionTest() throws BadRequestException {
        Account acc = new Account();
        acc.setAccountNum(1010101010L);
        acc.setBalanceAmt(1000.0d);
        acc.setAcctId(2L);
        this.mockServer.expect(requestTo(baseUrl + "/withdraw"))
                .andRespond(withStatus(HttpStatus.BAD_REQUEST));

        assertThrows(BadRequestException.class, () -> controller.custAcctWithdraw(900.0d, 1010101010L));
    }

    @Test
    public void deleteAccountTest() {
        Account acc = new Account();
        acc.setAccountNum(1010101010L);
        acc.setBalanceAmt(1000.0d);
        acc.setAcctId(2L);
        Customer cus = new Customer();
        cus.setAccounts(Collections.singletonList(acc));
        cus.setCustName("Vikram");
        this.mockServer.expect(requestTo(baseUrl + "/account"))
                .andRespond(withStatus(HttpStatus.OK));
        ResponseEntity<Account> res = controller.deleteCustAcct(1010101010L);

        assertEquals(2L, res.getBody().getAcctId());
        assertEquals(1000.0d, res.getBody().getBalanceAmt());
        assertEquals(10101010101L, res.getBody().getAccountNum());
    }

    @Test
    public void deleteAccountInternalServerExceptionTest() {
        Account acc = new Account();
        acc.setAccountNum(1010101010L);
        acc.setBalanceAmt(1000.0d);
        acc.setAcctId(2L);
        Customer cus = new Customer();
        cus.setAccounts(Collections.singletonList(acc));
        cus.setCustName("Vikram");
        this.mockServer.expect(requestTo(baseUrl + "/account"))
                .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR));
        ResponseEntity<Account> res = controller.deleteCustAcct(1010101010L);

    }

    @Test
    public void createAccount() throws BadRequestException {
        Account acc = new Account();
        acc.setAccountNum(1010101010L);
        acc.setBalanceAmt(1000.0d);
        acc.setAcctId(2L);
        Customer cus = new Customer();
        cus.setAccounts(Collections.singletonList(acc));
        cus.setCustName("Vikram");

        this.mockServer.expect(requestTo(baseUrl + "/account"))
                .andRespond(withStatus(HttpStatus.OK));
        ResponseEntity<Customer> res = controller.createCustAcct(cus);

        assertEquals("Vikram", res.getBody().getCustName());
        assertEquals(1000.0d, res.getBody().getAccounts().get(0).getBalanceAmt());
        assertEquals(2L, res.getBody().getAccounts().get(0).getAcctId());
    }

    @Test
    public void createAccountDepositAmtLimitException() throws BadRequestException {
        Account acc = new Account();
        acc.setAccountNum(1010101010L);
        acc.setBalanceAmt(10050.0d);
        acc.setAcctId(2L);
        Customer cus = new Customer();
        cus.setAccounts(Collections.singletonList(acc));
        cus.setCustName("Vikram");

        this.mockServer.expect(requestTo(baseUrl + "/account"))
                .andRespond(withStatus(HttpStatus.BAD_REQUEST));
        ResponseEntity<Customer> res = controller.createCustAcct(cus);

        assertThrows(BadRequestException.class, () -> controller.createCustAcct(cus));
    }

    @Test
    public void getAllAccountsTest() {
        Account acc = new Account();
        acc.setAccountNum(1010101010L);
        acc.setBalanceAmt(1000.0d);
        acc.setAcctId(2L);
        Customer cus = new Customer();
        cus.setAccounts(Collections.singletonList(acc));
        cus.setCustName("Vikram");

        Account acc1 = new Account();
        acc1.setAccountNum(2020202020L);
        acc1.setBalanceAmt(2000.0d);
        acc1.setAcctId(5L);
        Customer cus1 = new Customer();
        cus1.setAccounts(Collections.singletonList(acc1));
        cus1.setCustName("Prasad");

        this.mockServer.expect(requestTo(baseUrl + "/all-cust-accts"))
                .andRespond(withStatus(HttpStatus.BAD_REQUEST));
        ResponseEntity<List<Customer>> res = controller.getAllCustomerAccts();

        assertEquals("Vikram", res.getBody().get(0).getCustName());
        assertEquals(1000.0d, res.getBody().get(0).getAccounts().get(0).getBalanceAmt());
        assertEquals(2L, res.getBody().get(0).getAccounts().get(0).getAcctId());
        assertEquals(1010101010L, res.getBody().get(0).getAccounts().get(0).getAccountNum());

        assertEquals("Prasad", res.getBody().get(2).getCustName());
        assertEquals(2000.0d, res.getBody().get(2).getAccounts().get(0).getBalanceAmt());
        assertEquals(5L, res.getBody().get(2).getAccounts().get(0).getAcctId());
        assertEquals(2020202020L, res.getBody().get(2).getAccounts().get(0).getAccountNum());
    }

}
