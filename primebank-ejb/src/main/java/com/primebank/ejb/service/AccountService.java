package com.primebank.ejb.service;

import com.primebank.core.dto.request.AccountCreateRequestDTO;
import com.primebank.core.dto.response.ResponseDTO;
import jakarta.ejb.Local;

@Local
public interface AccountService {
    ResponseDTO<String> createAccount(AccountCreateRequestDTO dto);
}
