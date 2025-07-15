<%@ page import="com.primebank.core.entity.User" %>
<%@ page import="com.primebank.core.entity.enums.UserRole" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - PrimeBank</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <jsp:include page="/includes/header.jsp" />

    <%
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null || sessionUser.getUserRole() != UserRole.ADMIN) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return;
        }
    %>
    
    <main class="main-content">
        <div class="card">
            <div class="card-header">
                <h1 class="card-title">Administrator Dashboard</h1>
                <p class="card-subtitle">System Administration & User Management</p>
            </div>
            
            <div class="dashboard-grid">
                <div class="stat-card">
                    <div class="stat-number">1,247</div>
                    <div class="stat-label">Total Customers</div>
                </div>
                <div class="stat-card">
                    <div class="stat-number">45</div>
                    <div class="stat-label">Active Staff</div>
                </div>
                <div class="stat-card">
                    <div class="stat-number">12</div>
                    <div class="stat-label">System Alerts</div>
                </div>
                <div class="stat-card">
                    <div class="stat-number">99.8%</div>
                    <div class="stat-label">System Uptime</div>
                </div>
            </div>
        </div>
        
        <div class="card">
            <div class="card-header">
                <h2 class="card-title">All Customers</h2>
                <p class="card-subtitle">Complete customer database overview</p>
            </div>
            
            <div class="table-container">
                <table class="table">
                    <thead>
                        <tr>
                            <th>Customer ID</th>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Account Type</th>
                            <th>Status</th>
                            <th>Last Login</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>CUST001</td>
                            <td>John Smith</td>
                            <td>john.smith@email.com</td>
                            <td>Premium</td>
                            <td>Active</td>
                            <td>2024-01-15 09:30</td>
                        </tr>
                        <tr>
                            <td>CUST002</td>
                            <td>Sarah Johnson</td>
                            <td>sarah.j@email.com</td>
                            <td>Standard</td>
                            <td>Active</td>
                            <td>2024-01-14 14:22</td>
                        </tr>
                        <tr>
                            <td>CUST003</td>
                            <td>Michael Brown</td>
                            <td>m.brown@email.com</td>
                            <td>Business</td>
                            <td>Pending</td>
                            <td>2024-01-13 11:45</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </main>
    
    <jsp:include page="/includes/footer.jsp" />
</body>
</html>
