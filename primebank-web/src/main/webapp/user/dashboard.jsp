<%@ page import="com.primebank.core.entity.User" %>
<%@ page import="com.primebank.core.entity.enums.UserRole" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
                    <p class="card-subtitle">Account #: ****-****-****-5678</p>
                </div>
                <div class="stat-number">$12,450.92</div>
                <div class="stat-label">Available Balance</div>
            </div>

            <div class="card">
                <div class="card-header">
                    <h2 class="card-title">Debit Card</h2>
                    <p class="card-subtitle">Card #: ****-****-****-9012</p>
                </div>
                <div class="stat-number">$1,234.56</div>
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
                            <th>Balance</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>Jan 15, 2024</td>
                            <td>Online Purchase - Amazon</td>
                            <td>Checking</td>
                            <td>-$89.99</td>
                            <td>$5,247.83</td>
                        </tr>
                        <tr>
                            <td>Jan 14, 2024</td>
                            <td>Salary Deposit</td>
                            <td>Checking</td>
                            <td>+$3,200.00</td>
                            <td>$5,337.82</td>
                        </tr>
                        <tr>
                            <td>Jan 13, 2024</td>
                            <td>ATM Withdrawal</td>
                            <td>Checking</td>
                            <td>-$100.00</td>
                            <td>$2,137.82</td>
                        </tr>
                        <tr>
                            <td>Jan 12, 2024</td>
                            <td>Transfer to Savings</td>
                            <td>Checking</td>
                            <td>-$500.00</td>
                            <td>$2,237.82</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        

    </main>
    
    <jsp:include page="/includes/footer.jsp" />
</body>
</html>
