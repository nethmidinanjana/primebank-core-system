package com.primebank.ejb.service;

import com.primebank.core.dto.request.CustomerSaveRequestDTO;
import com.primebank.core.dto.response.CustomerAccountDTO;
import com.primebank.core.dto.response.ResponseDTO;
import com.primebank.core.entity.Customer;
import jakarta.ejb.Local;
import jakarta.ejb.Remote;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

@Local
public interface UserService {

    //CREATE
    ResponseDTO<String> createCustomer(CustomerSaveRequestDTO customerSaveRequestDTO);

    //READ
    ResponseDTO<Customer> getCustomerById(Long id);
    ResponseDTO<List<Customer>> getAllCustomers();

    //UPDATE
    ResponseDTO<String> updateCustomer(Long id, CustomerSaveRequestDTO customerSaveRequestDTO);

    //DELETE
    ResponseDTO<String> deleteCustomer(Long id);

    //SEARCH
    ResponseDTO<Customer> searchCustomerByNic(String nic);
    ResponseDTO<List<Customer>> searchCustomerByName(String name);
    ResponseDTO<Customer> findCustomerByEmailOrNic(String identifier);
    ResponseDTO<Customer> findCustomerByEmail(String email);


    // SECURITY OR METADATA
    boolean customerExistsByEmailOrNic(String email, String nic);


    long getCustomerCount();
    long getActiveEmployeeCount();


    List<CustomerAccountDTO> getCustomerWithAccounts();
}
