package com.primebank.core.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;

public class TransactionDTO implements Serializable {
    private String createdAtFormatted;
    private String description;
    private String accountType;
    private BigDecimal amount;
    private String to;

    public TransactionDTO() {
    }

    public TransactionDTO(String createdAtFormatted, String description, String accountType, BigDecimal amount, String to) {
        this.createdAtFormatted = createdAtFormatted;
        this.description = description;
        this.accountType = accountType;
        this.amount = amount;
        this.to = to;
    }

    public String getCreatedAtFormatted() {
        return createdAtFormatted;
    }

    public void setCreatedAtFormatted(String createdAtFormatted) {
        this.createdAtFormatted = createdAtFormatted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
