package com.primebank.web.servlet;

import com.primebank.core.entity.Transaction;
import com.primebank.ejb.service.AccountService;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/teller/dashboard")
public class TellerDashboardServlet extends HttpServlet {

    @EJB
    private AccountService accountService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Transaction> transactions = accountService.findTodayTransactions();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");

        List<Map<String, String>> formattedTransactions = new ArrayList<>();

        for (Transaction tx : transactions) {
            Map<String, String> row = new HashMap<>();

            // Format time
            row.put("time", tx.getCreatedAt().format(formatter));

            // Get customer name
            String customerName = "";
            if (tx.getFromAccount() != null && tx.getFromAccount().getOwner() != null) {
                customerName = tx.getFromAccount().getOwner().getFullName();
            } else if (tx.getToAccount() != null && tx.getToAccount().getOwner() != null) {
                customerName = tx.getToAccount().getOwner().getFullName();
            }

            row.put("customer", customerName);
            row.put("type", tx.getType().toString());
            row.put("amount", "Rs. " + tx.getAmount().toPlainString());
            row.put("status", "Completed");

            formattedTransactions.add(row);
        }

        long todayCount = accountService.countTodayTransactions();
        BigDecimal todayTotalAmount = accountService.sumTodayTransactionAmounts();

        req.setAttribute("todayCount", todayCount);
        req.setAttribute("todayTotalAmount", todayTotalAmount);


        req.setAttribute("transactions", formattedTransactions);
        req.getRequestDispatcher("/teller/dashboard.jsp").forward(req, resp);

    }
}
