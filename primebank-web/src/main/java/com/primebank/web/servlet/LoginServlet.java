package com.primebank.web.servlet;

import com.primebank.core.dto.response.ResponseDTO;
import com.primebank.ejb.service.UserService;
import com.primebank.ejb.util.PasswordUtil;
import jakarta.ejb.EJB;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {
    @EJB
    private UserService userService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        ResponseDTO<Map<String, Object>> result = userService.login(username, password, req);

        if (result.isStatus()) {
            String role = result.getData().get("role").toString();

            String redirectPath = switch (role){
                case "ADMIN" -> req.getContextPath() + "/admin/dashboard.jsp";
                case "MANAGER" -> req.getContextPath() + "/manager/dashboard.jsp";
                case "TELLER" -> req.getContextPath() + "/teller/dashboard.jsp";
                case "CUSTOMER" -> req.getContextPath() + "/user/dashboard.jsp";
                case "AUDITOR" -> req.getContextPath() + "/auditor/dashboard.jsp";
                default -> req.getContextPath() + "/";
            };

            resp.sendRedirect(redirectPath);
        } else {
            resp.sendRedirect(req.getContextPath() + "/auth/login.jsp?error=true");
        }
    }
}
