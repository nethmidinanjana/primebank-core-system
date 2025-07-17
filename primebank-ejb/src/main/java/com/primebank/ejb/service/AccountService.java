package com.primebank.ejb.service;

import com.primebank.core.dto.request.AccountCreateRequestDTO;
import com.primebank.core.dto.response.ResponseDTO;
import com.primebank.core.entity.Account;
import com.primebank.core.entity.Transaction;
import com.primebank.ejb.exception.InsufficientFundsException;
import jakarta.ejb.Local;

import java.math.BigDecimal;
import java.util.List;

@Local
public interface AccountService {
    ResponseDTO<String> createAccount(AccountCreateRequestDTO dto);

    ResponseDTO<String> transferFunds(String fromAccountNo, String toAccountNo, BigDecimal amount, String description)
            throws InsufficientFundsException;

    Account getAccountByAccountNumber(String accountNumber);

    List<Transaction> findTodayTransactions();

    long countTodayTransactions();
    BigDecimal sumTodayTransactionAmounts();

    Account getAccountByCustomerId(Long customerId);

    List<Transaction> getTransactionsByAccountId(Long accountId);
}
