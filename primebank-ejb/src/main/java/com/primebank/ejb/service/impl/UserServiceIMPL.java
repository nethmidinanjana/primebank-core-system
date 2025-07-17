package com.primebank.ejb.service.impl;

import com.primebank.core.dto.request.CustomerSaveRequestDTO;
import com.primebank.core.dto.response.CustomerAccountDTO;
import com.primebank.core.dto.response.ResponseDTO;
import com.primebank.core.entity.Customer;
import com.primebank.core.entity.enums.Status;
import com.primebank.ejb.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.modelmapper.ModelMapper;

import java.util.List;


@Stateless
public class UserServiceIMPL implements UserService {

    @PersistenceContext(unitName = "primeBankPU")
    private EntityManager em;

    @Inject
    private ModelMapper modelMapper;

    @Override
    @RolesAllowed({"ADMIN","TELLER","MANAGER"})
    public ResponseDTO<String> createCustomer(CustomerSaveRequestDTO dto) {
        try {

            if(dto.getNic() != null && !dto.getNic().isBlank()){
                Long nicCount = em.createNamedQuery("Customer.getCustomerCountByNic", Long.class).setParameter("nic", dto.getNic()).getSingleResult();

                if(nicCount > 0){
                    return new ResponseDTO<>(null, false, "Customer with NIC '" + dto.getNic() + "' already exists");
                }
            }

            if(dto.getBirthCertificateNo() != null && !dto.getBirthCertificateNo().isBlank()){
                Long bcNo = em.createNamedQuery("Customer.getCustomerCountByBirthCert", Long.class).setParameter("bcNo", dto.getBirthCertificateNo()).getSingleResult();

                if(bcNo > 0){
                    return new ResponseDTO<>(null, false, "Customer with Birth Certificate No. " + bcNo + " already exists");
                }
            }
            Customer customer = modelMapper.map(dto, Customer.class);

            customer.getNic();
            em.persist(customer);

            return new ResponseDTO<>(null, true, "Customer saved successfully");
        } catch (Exception e) {
            return new ResponseDTO<>(null, false, "Failed to create customer: " + e.getMessage());
        }
    }

    @Override
    @RolesAllowed({"ADMIN","TELLER","MANAGER"})
    public ResponseDTO<Customer> getCustomerById(Long id) {
        try {
            Customer customer = em.find(Customer.class, id);

            if(customer == null) {
                return new ResponseDTO<>(null, false, "Customer not found with ID: " + id);
            }
            return new ResponseDTO<>(customer, true, "Customer found");
        } catch (Exception e) {
            return new ResponseDTO<>(null, false, "Error getting customer: " + e.getMessage());
        }
    }

    @Override
    @RolesAllowed({"ADMIN","TELLER","MANAGER","AUDITOR"})
    public ResponseDTO<List<Customer>> getAllCustomers() {
        try {
            List<Customer> customers = em.createNamedQuery("Customer.findAll", Customer.class).getResultList();
            return new ResponseDTO<>(customers, true, "All customers found");
        } catch (Exception e) {
            return new ResponseDTO<>(null, false, "Error getting all customers: " + e.getMessage());
        }
    }

    @Override
    @RolesAllowed({"ADMIN","TELLER","MANAGER"})
    public ResponseDTO<String> updateCustomer(Long id, CustomerSaveRequestDTO customerSaveRequestDTO) {
        try {
            Customer customer = em.find(Customer.class, id);

            if(customer == null) {
                return new ResponseDTO<>(null, false, "Customer not found with ID: " + id);
            }

            customer.setFullName(customerSaveRequestDTO.getFullName());
            customer.setEmail(customerSaveRequestDTO.getEmail());
            customer.setAddress(customerSaveRequestDTO.getAddress());
            customer.setNic(customerSaveRequestDTO.getNic());
            customer.setBirthCertificateNo(customerSaveRequestDTO.getBirthCertificateNo());
            customer.setContactNo(customerSaveRequestDTO.getContactNo());
            customer.setDateOfBirth(customerSaveRequestDTO.getDateOfBirth());
            customer.setGender(customerSaveRequestDTO.getGender());
            customer.setPhoto(customerSaveRequestDTO.getPhoto());
            em.persist(customer);

            return new ResponseDTO<>(null, true, "Customer updated successfully");
        } catch (Exception e) {
            return new ResponseDTO<>(null, false, "Failed to update customer: " + e.getMessage());
        }
    }

    @Override
    @RolesAllowed({"ADMIN"})
    public ResponseDTO<String> deleteCustomer(Long id) {
        try {
            Customer customer = em.find(Customer.class, id);

            if(customer == null) {
                return new ResponseDTO<>(null, false, "Customer not found with ID: " + id);
            }

            customer.setStatus(Status.INACTIVE);
            em.merge(customer);
            return new ResponseDTO<>(null, true, "Customer deleted successfully");
        } catch (Exception e) {
            return new ResponseDTO<>(null, false, "Failed to delete customer: " + e.getMessage());
        }
    }

    @Override
    @RolesAllowed({"ADMIN","TELLER","MANAGER"})
    public ResponseDTO<Customer> searchCustomerByNic(String nic) {
        Customer customer = em.createNamedQuery("Customer.findByNic", Customer.class).setParameter("nic", nic).getSingleResult();

        if(customer == null) {
            return new ResponseDTO<>(null, false, "Customer not found with NIC: " + nic);
        }else{
            return new ResponseDTO<>(customer, true, "Customer found");
        }
    }

    @Override
    @RolesAllowed({"ADMIN","TELLER","MANAGER"})
    public ResponseDTO<List<Customer>> searchCustomerByName(String name) {
        try {
            List<Customer> customerList = em.createNamedQuery("Customer.findByName", Customer.class).setParameter("name", name).getResultList();

            if(customerList.isEmpty()){
                return new ResponseDTO<>(null, false, "Customer not found with name: " + name);
            }

            return new ResponseDTO<>(customerList, true, "Customers found");
        } catch (Exception e) {
            return new ResponseDTO<>(null, false, "Error getting customer: " + e.getMessage());
        }
    }

    @Override
    @RolesAllowed({"ADMIN", "TELLER", "MANAGER"})
    public ResponseDTO<Customer> findCustomerByEmailOrNic(String identifier) {
        try {
            Customer customer = em.createNamedQuery(
                            "Customer.findByEmailOrNic", Customer.class)
                    .setParameter("id", identifier)
                    .getSingleResult();

            return new ResponseDTO<>(customer, true, "Customer found");
        } catch (NoResultException e) {
            return new ResponseDTO<>(null, false, "Customer not found");
        }
    }

    @Override
    @RolesAllowed({"ADMIN", "TELLER", "MANAGER"})
    public ResponseDTO<Customer> findCustomerByEmail(String email) {
        try {
            Customer customer = em.createNamedQuery(
                            "Customer.findByEmail", Customer.class)
                    .setParameter("email", email)
                    .getSingleResult();

            return new ResponseDTO<>(customer, true, "Customer found");
        } catch (NoResultException e) {
            return new ResponseDTO<>(null, false, "Customer not found");
        }
    }


    @Override
    @RolesAllowed({"ADMIN","TELLER","MANAGER"})
    public boolean customerExistsByEmailOrNic(String email, String nic) {
        Long count = em.createNamedQuery("Customer.existsByEmailOrNic", Long.class).setParameter("email", email).setParameter("nic", nic).getSingleResult();
        return count > 0;
    }

    @Override
    public long getCustomerCount() {
        return em.createQuery("SELECT COUNT(c) FROM Customer c", Long.class).getSingleResult();
    }

    @Override
    public long getActiveEmployeeCount() {
        return em.createQuery("SELECT COUNT(e) FROM Employee e WHERE e.status = :status", Long.class)
                .setParameter("status", Status.ACTIVE)
                .getSingleResult();
    }

    @Override
    public List<CustomerAccountDTO> getCustomerWithAccounts() {

        List<CustomerAccountDTO> results = em.createQuery(
                "SELECT new com.primebank.core.dto.response.CustomerAccountDTO(" +
                        "c.id, c.fullName, c.email, a.accountType, a.status) " +
                        "FROM Account a JOIN a.owner c",
                CustomerAccountDTO.class
        ).getResultList();

        for (CustomerAccountDTO dto : results) {
            System.out.println("DTO: " + dto.getName() + " | " + dto.getEmail() +
                    " | " + dto.getAccountType() + " | " + dto.getAccountStatus());
        }

        return results;
    }
}
