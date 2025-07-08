package com.primebank.ejb.service;

import com.primebank.core.dto.request.CustomerSaveRequestDTO;
import com.primebank.core.dto.response.ResponseDTO;
import com.primebank.core.entity.Customer;
import jakarta.ejb.Local;

@Local
public interface UserService {

    ResponseDTO<String> createCustomer(CustomerSaveRequestDTO customerSaveRequestDTO);
}
