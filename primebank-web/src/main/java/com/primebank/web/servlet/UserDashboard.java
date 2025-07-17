package com.primebank.web.servlet;

import com.primebank.core.dto.response.TransactionDTO;
import com.primebank.core.entity.Account;
import com.primebank.core.entity.Customer;
import com.primebank.core.entity.Transaction;
import com.primebank.core.entity.User;
import com.primebank.core.entity.enums.UserRole;
import com.primebank.ejb.service.AccountService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/user/dashboard")
public class UserDashboard extends HttpServlet {

    @EJB
    private AccountService accountService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("user");

        if(user.getUserRole() == UserRole.CUSTOMER){
            Customer customer = user.getCustomer();
            Long id = customer.getId();

            Account account = accountService.getAccountByCustomerId(id);

            if(account != null){
                req.setAttribute("accountNumber", account.getAccountNumber());
                req.setAttribute("balance", account.getBalance());

                List<Transaction> transactions = accountService.getTransactionsByAccountId(account.getId());

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm a");

                List<TransactionDTO> dtoList = transactions.stream().map(tx -> {
                    String formattedDate = tx.getCreatedAt().format(formatter);

                    String accountType = tx.getFromAccount().getAccountType().toString();
                    BigDecimal amount = tx.getAmount();
                    String to = (tx.getToAccount() != null && tx.getToAccount().getOwner() != null)
                            ? tx.getToAccount().getOwner().getFullName()
                            : "N/A";

                    return new TransactionDTO(formattedDate, tx.getDescription(), accountType, amount, to);
                }).collect(Collectors.toList());

                req.setAttribute("transactionDTOList", dtoList);

            }

            req.getRequestDispatcher("/user/dashboard.jsp").forward(req, resp);
        }else{
            req.getRequestDispatcher("/").forward(req, resp);
        }
    }
}
