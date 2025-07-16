package com.primebank.ejb.security;

import com.primebank.ejb.service.LoginService;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;

import java.util.Set;

@Alternative
@Priority(1)
@ApplicationScoped
public class AppIdentityStore implements IdentityStore {

    @Inject
    private LoginService loginService;

    @Override
    public CredentialValidationResult validate(Credential credential) {
        System.out.println("validate");
        if (credential instanceof UsernamePasswordCredential) {
            UsernamePasswordCredential upc = (UsernamePasswordCredential) credential;

            if (loginService.validate(upc.getCaller(), upc.getPasswordAsString())) {
                Set<String> roles = loginService.getRoles(upc.getCaller());

                return new CredentialValidationResult(upc.getCaller(), roles);

            }
        }

        return CredentialValidationResult.INVALID_RESULT;
    }
}