package com.primebank.web.rest;

import com.primebank.core.dto.request.AccountCreateRequestDTO;
import com.primebank.core.dto.response.ResponseDTO;
import com.primebank.ejb.service.AccountService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/accounts")
public class AccountResource {

    @EJB
    private AccountService accountService;

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "TELLER", "MANAGER"})
    public ResponseDTO<String> createAccount(AccountCreateRequestDTO dto) {
        return accountService.createAccount(dto);
    }

}
