package com.primebank.ejb.service.impl;

import com.primebank.core.dto.request.AccountCreateRequestDTO;
import com.primebank.core.dto.response.ResponseDTO;
import com.primebank.core.entity.Account;
import com.primebank.core.entity.Customer;
import com.primebank.core.entity.enums.AccountType;
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

@Stateless
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

            em.flush();
            interestCalculationTimer.scheduleInterestCalculation(account.getId());

            return new ResponseDTO<>(null, true, "Account created successfully");
        } catch (Exception e) {
            return new ResponseDTO<>(null, false, "Failed to create account: " + e.getMessage());
        }
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
