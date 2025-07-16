package com.primebank.web.rest;

import com.primebank.core.dto.request.AccountCreateRequestDTO;
import com.primebank.core.dto.request.TransferRequestDTO;
import com.primebank.core.dto.response.ResponseDTO;
import com.primebank.core.entity.Account;
import com.primebank.core.entity.Customer;
import com.primebank.core.entity.User;
import com.primebank.core.entity.enums.UserRole;
import com.primebank.ejb.exception.InsufficientFundsException;
import com.primebank.ejb.service.AccountService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;

@Path("/accounts")
public class AccountResource {

    @EJB
    private AccountService accountService;

    @Context
    private SecurityContext securityContext;

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "TELLER", "MANAGER"})
    public ResponseDTO<String> createAccount(AccountCreateRequestDTO dto) {
        return accountService.createAccount(dto);
    }

    @POST
    @Path("/transfer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"CUSTOMER", "TELLER", "MANAGER"})
    public ResponseDTO<String> transfer(TransferRequestDTO dto, @Context HttpServletRequest request) {
        try {

            // Get currently logged-in User object from session
            User loggedInUser = (User) request.getSession().getAttribute("user");

            if (loggedInUser == null) {
                return new ResponseDTO<>(null, false, "User not logged in.");
            }

            Account fromAccount = accountService.getAccountByAccountNumber(dto.getFromAccount());

            if (fromAccount == null) {
                return new ResponseDTO<>(null, false, "Source account not found.");
            }

            // Only validate ownership if user is CUSTOMER
            if (loggedInUser.getUserRole() == UserRole.CUSTOMER) {
                Customer owner = fromAccount.getOwner();

                if (owner == null || !owner.getId().equals(loggedInUser.getCustomer().getId())) {
                    return new ResponseDTO<>(null, false, "You are not authorized to transfer from this account.");
                }
            }

            return accountService.transferFunds(
                    dto.getFromAccount(),
                    dto.getToAccount(),
                    dto.getAmount(),
                    dto.getDescription()
            );

        } catch (InsufficientFundsException e) {
            return new ResponseDTO<>(null, false, e.getMessage());
        } catch (Exception e) {
            return new ResponseDTO<>(null, false, "Transfer failed: " + e.getMessage());
        }
    }


}
