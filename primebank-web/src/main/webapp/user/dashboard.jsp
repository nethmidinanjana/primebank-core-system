<%@ page import="com.primebank.core.entity.User" %>
<%@ page import="com.primebank.core.entity.enums.UserRole" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Account - PrimeBank</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <jsp:include page="/includes/header.jsp" />

    <%
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null || sessionUser.getUserRole() != UserRole.CUSTOMER) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return;
        }
    %>
    
    <main class="main-content">
        <div class="card">
            <div class="card-header">
                <h1 class="card-title">My Banking Dashboard</h1>
                <p class="card-subtitle">Personal Account Overview</p>
            </div>
            
            <div class="alert alert-success">
                <strong>Welcome back!</strong> Your last login was on January 14, 2024 at 3:45 PM
            </div>
        </div>
        
        <div class="dashboard-grid">
            <div class="card">
                <div class="card-header">
                    <h2 class="card-title">Savings Account</h2>
                    <p class="card-subtitle">Account #: ${accountNumber}</p>
                </div>
                <div class="stat-number">Rs. ${balance}</div>
                <div class="stat-label">Available Balance</div>
            </div>

            <div class="card">
                <div class="card-header">
                    <h2 class="card-title">Debit Card</h2>
                    <p class="card-subtitle">Card #: ${accountNumber}</p>
                </div>
                <div class="stat-number">Rs. ${balance}</div>
                <div class="stat-label">Current Balance</div>
            </div>
        </div>

        <div class="card">
            <div class="card-header">
                <h2 class="card-title">Quick Actions</h2>
                <p class="card-subtitle">Common banking operations</p>
            </div>

            <div class="dashboard-grid">
                <a href="transfer.jsp" class="btn btn-primary">Transfer Money</a>
                <button class="btn btn-secondary">Pay Bills</button>
                <button class="btn btn-secondary">View Statements</button>
                <button class="btn btn-secondary">Contact Support</button>
            </div>
        </div>
        
        <div class="card">
            <div class="card-header">
                <h2 class="card-title">Recent Transactions</h2>
                <p class="card-subtitle">Your latest account activity</p>
            </div>
            
            <div class="table-container">
                <table class="table">
                    <thead>
                        <tr>
                            <th>Date</th>
                            <th>Description</th>
                            <th>Account</th>
                            <th>Amount</th>
                            <th>To</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="tx" items="${transactionDTOList}">
                            <tr>
                                <td>${tx.createdAtFormatted}</td>
                                <td>${tx.description}</td>
                                <td>${tx.accountType}</td>
                                <td>Rs. ${tx.amount}</td>
                                <td>${tx.to}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        

    </main>
    
    <jsp:include page="/includes/footer.jsp" />
</body>
</html>
