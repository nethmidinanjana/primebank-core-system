package com.primebank.ejb.service.impl;

import com.primebank.core.dto.request.AccountCreateRequestDTO;
import com.primebank.core.dto.response.ResponseDTO;
import com.primebank.core.entity.Account;
import com.primebank.core.entity.Customer;
import com.primebank.core.entity.Transaction;
import com.primebank.core.entity.enums.AccountType;
import com.primebank.core.entity.enums.TransactionType;
import com.primebank.core.interceptor.Auditable;
import com.primebank.ejb.exception.InsufficientFundsException;
import com.primebank.ejb.service.AccountService;
import com.primebank.ejb.service.UserService;
import com.primebank.ejb.service.timer.InterestCalculationTimer;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Stateless
@Auditable
public class AccountServiceIMPL implements AccountService {

    @PersistenceContext(unitName = "primeBankPU")
    private EntityManager em;

    @Inject
    private UserService userService;

    @Inject
    private InterestCalculationTimer interestCalculationTimer;

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ResponseDTO<String> createAccount(AccountCreateRequestDTO dto) {
        try {
            Customer customer = userService.findCustomerByEmailOrNic(dto.getCustomerIdentifier()).getData();

            if (customer == null) {
                return new ResponseDTO<>(null, false, "Customer not found");
            }

            Customer guardian = null;
            if (dto.getGuardianEmail() != null && !dto.getGuardianEmail().isBlank()) {
                guardian = userService.findCustomerByEmail(dto.getGuardianEmail()).getData();
                if (guardian == null) {
                    return new ResponseDTO<>(null, false, "Guardian not found");
                }
            }

            Account account = new Account();
            account.setAccountNumber(generateAccountNumber());
            account.setAccountType(AccountType.valueOf(dto.getAccountType()));
            account.setBalance(new BigDecimal(String.valueOf(dto.getInitialDeposit())));
            account.setOwner(customer);
            account.setGuardian(guardian);

            em.persist(account);

            Transaction transaction = new Transaction();
            transaction.setFromAccount(null); // Bank
            transaction.setToAccount(account);
            transaction.setAmount(account.getBalance());
            transaction.setType(TransactionType.DEPOSIT);
            transaction.setCreatedAt(LocalDateTime.now());
            transaction.setDescription("Initial deposit at account creation");

            em.persist(transaction);

            em.flush();
            interestCalculationTimer.scheduleInterestCalculation(account.getId());

            return new ResponseDTO<>(null, true, "Account created successfully");
        } catch (Exception e) {
            return new ResponseDTO<>(null, false, "Failed to create account: " + e.getMessage());
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ResponseDTO<String> transferFunds(String fromAccountNo, String toAccountNo, BigDecimal amount, String description)
            throws InsufficientFundsException {

        Account fromAccount = getAccountByAccountNumber(fromAccountNo);

        Account toAccount = getAccountByAccountNumber(toAccountNo);

        if (fromAccount == null || toAccount == null) {
            return new ResponseDTO<>(null, false, "Account not found");
        }

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient funds in source account");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        em.merge(fromAccount);
        em.merge(toAccount);

        Transaction transaction = new Transaction();
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);
        transaction.setAmount(amount);
        transaction.setType(TransactionType.TRANSFER);
        transaction.setDescription(description != null ? description : "Fund Transfer");

        em.persist(transaction);

        return new ResponseDTO<>(null, true, "Transfer successful");
    }

    @Override
    public Account getAccountByAccountNumber(String accountNumber) {
        List<Account> results = em.createNamedQuery("Account.findByAccountNumber", Account.class)
                .setParameter("accountNumber", accountNumber)
                .getResultList();

        return results.isEmpty() ? null : results.get(0);
    }


    private String generateAccountNumber() {
        String accNo;
        do {
            accNo = "AC" + (System.currentTimeMillis() % 1_000_000_000);
        } while (accountNumberExists(accNo));
        return accNo;
    }

    private boolean accountNumberExists(String accNo) {
        Long count = em.createQuery(
                        "SELECT COUNT(a) FROM Account a WHERE a.accountNumber = :accNo", Long.class)
                .setParameter("accNo", accNo)
                .getSingleResult();
        return count > 0;
    }

}
