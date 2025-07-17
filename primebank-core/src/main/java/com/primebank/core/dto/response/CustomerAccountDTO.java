package com.primebank.core.dto.response;

import com.primebank.core.entity.Account;
import com.primebank.core.entity.enums.AccountType;
import com.primebank.core.entity.enums.Status;

import java.io.Serializable;

public class CustomerAccountDTO implements Serializable {
    private Long customerId;
    private String name;
    private String email;
    private AccountType accountType;
    private Status accountStatus;

    public CustomerAccountDTO() {
    }

    public CustomerAccountDTO(Long customerId, String name, String email, AccountType accountType, Status accountStatus) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.accountType = accountType;
        this.accountStatus = accountStatus;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Status getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Status accountStatus) {
        this.accountStatus = accountStatus;
    }
}
