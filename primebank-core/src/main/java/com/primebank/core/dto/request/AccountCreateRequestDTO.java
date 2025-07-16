package com.primebank.core.dto.request;

import java.io.Serializable;
import java.math.BigDecimal;

public class AccountCreateRequestDTO implements Serializable {

    private String customerIdentifier; // can be NIC or email
    private String accountType;
    private BigDecimal initialDeposit;
    private String guardianEmail;

    public AccountCreateRequestDTO() {
    }

    public AccountCreateRequestDTO(String customerIdentifier, String accountType, BigDecimal initialDeposit, String guardianEmail) {
        this.customerIdentifier = customerIdentifier;
        this.accountType = accountType;
        this.initialDeposit = initialDeposit;
        this.guardianEmail = guardianEmail;
    }

    public String getCustomerIdentifier() {
        return customerIdentifier;
    }

    public void setCustomerIdentifier(String customerIdentifier) {
        this.customerIdentifier = customerIdentifier;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getInitialDeposit() {
        return initialDeposit;
    }

    public void setInitialDeposit(BigDecimal initialDeposit) {
        this.initialDeposit = initialDeposit;
    }

    public String getGuardianEmail() {
        return guardianEmail;
    }

    public void setGuardianEmail(String guardianEmail) {
        this.guardianEmail = guardianEmail;
    }
}
