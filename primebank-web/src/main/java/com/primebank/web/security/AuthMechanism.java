package com.primebank.web.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import jakarta.security.enterprise.authentication.mechanism.http.AutoApplySession;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

@AutoApplySession
@ApplicationScoped
public class AuthMechanism implements HttpAuthenticationMechanism {

    @Inject
    private IdentityStore identityStore;

    private static final Set<String> WHITELIST = Set.of(
            "/auth/login.jsp",
            "/auth/login",
            "/public",
            "/css",
            "/js",
            "/images",
            "/"
    );

    private boolean isWhiteListed(String path) {
        return WHITELIST.stream().anyMatch(path::startsWith);
    }

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext context) throws AuthenticationException {
        String path = request.getServletPath();

//        if (isWhiteListed(path)) {
//            return context.doNothing(); // Public route
//        }

        AuthenticationParameters authParams = context.getAuthParameters();
        System.out.println(authParams.getCredential());

        if (authParams.getCredential() != null) {
            CredentialValidationResult result = identityStore.validate(authParams.getCredential());

            if (result.getStatus() == CredentialValidationResult.Status.VALID) {
                return context.notifyContainerAboutLogin(result);
            } else {
                return AuthenticationStatus.SEND_FAILURE;
            }
        }

        if (context.isProtected()) {
            try {
                response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
                return AuthenticationStatus.SEND_CONTINUE;
            } catch (IOException e) {
                throw new RuntimeException("Redirect to login failed",e);
            }
        }

        return context.doNothing();
    }
}
