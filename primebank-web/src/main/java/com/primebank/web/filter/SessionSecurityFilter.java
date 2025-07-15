package com.primebank.web.filter;

import com.primebank.core.entity.User;
import jakarta.annotation.Priority;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.security.Principal;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class SessionSecurityFilter implements ContainerRequestFilter {

    @Context
    private HttpServletRequest request;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {

        User user = (User) request.getSession().getAttribute("user");

        if(user != null) {
            SecurityContext securityContext = new SecurityContext() {
                @Override
                public Principal getUserPrincipal() {
                    return () -> user.getUsername();
                }

                @Override
                public boolean isUserInRole(String role) {
                    return user.getUserRole().name().equalsIgnoreCase(role);
                }

                @Override
                public boolean isSecure() {
                    return request.isSecure();
                }

                @Override
                public String getAuthenticationScheme() {
                    return "SESSION";
                }
            };

            containerRequestContext.setSecurityContext(securityContext);
        }
    }
}
