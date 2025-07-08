package com.primebank.ejb.service.impl;

import com.primebank.core.dto.request.CustomerSaveRequestDTO;
import com.primebank.core.dto.response.ResponseDTO;
import com.primebank.core.entity.Customer;
import com.primebank.ejb.service.UserService;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


@Stateless
public class UserServiceIMPL implements UserService {

    @PersistenceContext(unitName = "primeBankPU")
    private EntityManager em;

    @Override
    public ResponseDTO<String> createCustomer(CustomerSaveRequestDTO dto) {
        try {
            Customer customer = new Customer();
            customer.setFullName(dto.getFullName());
            customer.setEmail(dto.getEmail());
            customer.setAddress(dto.getAddress());
            customer.setNic(dto.getNic());
            customer.setBirthCertificateNo(dto.getBirthCertificateNo());
            customer.setContactNo(dto.getContactNo());
            customer.setDateOfBirth(dto.getDateOfBirth());
            customer.setGender(dto.getGender());
            customer.setPhoto(dto.getPhoto());
            em.persist(customer);

            return new ResponseDTO<>(null, true, "Customer saved successfully");
        } catch (Exception e) {
            return new ResponseDTO<>(null, false, "Failed to create customer: " + e.getMessage());
        }
    }
}
