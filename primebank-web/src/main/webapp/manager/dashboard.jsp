<%@ page import="com.primebank.core.entity.User" %>
<%@ page import="com.primebank.core.entity.enums.UserRole" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manager Dashboard - PrimeBank</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <jsp:include page="/includes/header.jsp" />

    <%
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null || sessionUser.getUserRole() != UserRole.MANAGER) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return;
        }
    %>
    
    <main class="main-content">
        <div class="card">
            <div class="card-header">
                <h1 class="card-title">Manager Dashboard</h1>
                <p class="card-subtitle">Branch Management & Performance Overview</p>
            </div>
            
            <div class="dashboard-grid">
                <div class="stat-card">
                    <div class="stat-number">$2.4M</div>
                    <div class="stat-label">Daily Transactions</div>
                </div>
                <div class="stat-card">
                    <div class="stat-number">156</div>
                    <div class="stat-label">New Accounts</div>
                </div>
                <div class="stat-card">
                    <div class="stat-number">8</div>
                    <div class="stat-label">Active Tellers</div>
                </div>
                <div class="stat-card">
                    <div class="stat-number">94%</div>
                    <div class="stat-label">Customer Satisfaction</div>
                </div>
            </div>
        </div>
        
        <div class="card">
            <div class="card-header">
                <h2 class="card-title">Branch Performance</h2>
                <p class="card-subtitle">Key metrics and operational data</p>
            </div>
            
            <div class="alert alert-info">
                <strong>Branch Status:</strong> All systems operational. Peak hours: 11:00 AM - 2:00 PM
            </div>
            
            <div class="table-container">
                <table class="table">
                    <thead>
                        <tr>
                            <th>Metric</th>
                            <th>Today</th>
                            <th>This Week</th>
                            <th>This Month</th>
                            <th>Target</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>New Accounts</td>
                            <td>12</td>
                            <td>89</td>
                            <td>342</td>
                            <td>400</td>
                        </tr>
                        <tr>
                            <td>Loan Applications</td>
                            <td>5</td>
                            <td>23</td>
                            <td>156</td>
                            <td>200</td>
                        </tr>
                        <tr>
                            <td>Customer Visits</td>
                            <td>234</td>
                            <td>1,456</td>
                            <td>6,789</td>
                            <td>7,000</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </main>
    
    <jsp:include page="/includes/footer.jsp" />
</body>
</html>
