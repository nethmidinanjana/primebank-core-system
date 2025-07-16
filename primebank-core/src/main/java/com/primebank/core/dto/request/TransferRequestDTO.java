package com.primebank.core.dto.request;

import java.io.Serializable;
import java.math.BigDecimal;

public class TransferRequestDTO implements Serializable {
    private String fromAccount;
    private String toAccount;
    private BigDecimal amount;
    private String description;

    public TransferRequestDTO() {
    }

    public TransferRequestDTO(String fromAccount, String toAccount, BigDecimal amount, String description) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.description = description;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
