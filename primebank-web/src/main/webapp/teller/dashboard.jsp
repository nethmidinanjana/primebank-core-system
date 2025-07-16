<%@ page import="com.primebank.core.entity.User" %>
<%@ page import="com.primebank.core.entity.enums.UserRole" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Teller Dashboard - PrimeBank</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <jsp:include page="/includes/header.jsp" />

    <%
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null || sessionUser.getUserRole() != UserRole.TELLER) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return;
        }
    %>
    
    <main class="main-content">
        <div class="card">
            <div class="card-header">
                <h1 class="card-title">Teller Dashboard</h1>
                <p class="card-subtitle">Customer Transactions & Services</p>
            </div>
            
            <div class="dashboard-grid">
                <div class="stat-card">
                    <div class="stat-number">47</div>
                    <div class="stat-label">Transactions Today</div>
                </div>
                <div class="stat-card">
                    <div class="stat-number">$45,230</div>
                    <div class="stat-label">Total Amount</div>
                </div>
                <div class="stat-card">
                    <div class="stat-number">3</div>
                    <div class="stat-label">Queue Length</div>
                </div>
                <div class="stat-card">
                    <div class="stat-number">2.5 min</div>
                    <div class="stat-label">Avg. Service Time</div>
                </div>
            </div>
        </div>

        <div class="card">
            <div class="card-header">
                <h2 class="card-title">Quick Actions</h2>
                <p class="card-subtitle">Common teller operations</p>
            </div>

            <div class="dashboard-grid">
                <a href="create-customer.jsp" class="btn btn-primary">Create Customer</a>
                <a href="create-account.jsp" class="btn btn-secondary">Create Account</a>
                <button class="btn btn-secondary">Account Lookup</button>
                <button class="btn btn-secondary">Print Statement</button>
                <button class="btn btn-secondary">Balance Inquiry</button>
            </div>
        </div>
        
        <div class="card">
            <div class="card-header">
                <h2 class="card-title">Recent Transactions</h2>
                <p class="card-subtitle">Latest customer transactions processed</p>
            </div>
            
            <div class="table-container">
                <table class="table">
                    <thead>
                        <tr>
                            <th>Time</th>
                            <th>Customer</th>
                            <th>Transaction Type</th>
                            <th>Amount</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>14:32</td>
                            <td>Alice Cooper</td>
                            <td>Cash Deposit</td>
                            <td>$1,250.00</td>
                            <td>Completed</td>
                        </tr>
                        <tr>
                            <td>14:28</td>
                            <td>Bob Wilson</td>
                            <td>Cash Withdrawal</td>
                            <td>$500.00</td>
                            <td>Completed</td>
                        </tr>
                        <tr>
                            <td>14:25</td>
                            <td>Carol Davis</td>
                            <td>Check Deposit</td>
                            <td>$2,100.00</td>
                            <td>Processing</td>
                        </tr>
                        <tr>
                            <td>14:20</td>
                            <td>David Miller</td>
                            <td>Account Transfer</td>
                            <td>$750.00</td>
                            <td>Completed</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </main>
    
    <jsp:include page="/includes/footer.jsp" />
</body>
</html>
