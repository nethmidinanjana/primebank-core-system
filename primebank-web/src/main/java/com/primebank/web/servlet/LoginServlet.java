package com.primebank.web.servlet;

import com.primebank.core.entity.User;
import com.primebank.ejb.service.LoginService;
import com.primebank.ejb.util.PasswordUtil;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {

    @Inject
    private SecurityContext securityContext;

    @Inject
    private LoginService loginService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        AuthenticationParameters parameters = AuthenticationParameters
                .withParams()
                .credential(new UsernamePasswordCredential(username, password));

        AuthenticationStatus status = securityContext.authenticate(req, resp, parameters);

        if (status == AuthenticationStatus.SUCCESS) {
            System.out.println("✅ Login successful");

            User user = loginService.getUserByUsername(username);
            req.getSession().setAttribute("user", user);

            // Redirect based on role
            if (req.isUserInRole("ADMIN")) {
                resp.sendRedirect(req.getContextPath() + "/admin/dashboard.jsp");
            } else if (req.isUserInRole("MANAGER")) {
                resp.sendRedirect(req.getContextPath() + "/manager/dashboard.jsp");
            } else if (req.isUserInRole("TELLER")) {
                resp.sendRedirect(req.getContextPath() + "/teller/dashboard.jsp");
            } else if (req.isUserInRole("AUDITOR")) {
                resp.sendRedirect(req.getContextPath() + "/auditor/dashboard.jsp");
            }else if (req.isUserInRole("CUSTOMER")) {
                resp.sendRedirect(req.getContextPath() + "/user/dashboard.jsp");
            } else {
                resp.sendRedirect(req.getContextPath() + "/");
            }
        } else {
            System.out.println("Login failed: " + status);
            resp.sendRedirect(req.getContextPath() + "/auth/login.jsp?error=true");
        }
    }
}
