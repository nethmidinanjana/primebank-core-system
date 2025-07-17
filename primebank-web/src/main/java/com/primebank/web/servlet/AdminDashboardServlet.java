package com.primebank.web.servlet;

import com.primebank.core.dto.response.CustomerAccountDTO;
import com.primebank.ejb.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/dashboard")
@RolesAllowed("ADMIN")
public class AdminDashboardServlet extends HttpServlet {

    @EJB
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        long customerCount = userService.getCustomerCount();
        long employeeCount = userService.getActiveEmployeeCount();
        List<CustomerAccountDTO> customers = userService.getCustomerWithAccounts();

        req.setAttribute("customerCount", customerCount);
        req.setAttribute("employeeCount", employeeCount);
        req.setAttribute("customerList", customers);

        req.getRequestDispatcher("/admin/dashboard.jsp").forward(req, resp);
    }
}
