<%@ page import="com.primebank.core.entity.User" %>
<%@ page import="com.primebank.core.entity.enums.UserRole" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Auditor Dashboard - PrimeBank</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <jsp:include page="/includes/header.jsp" />

    <%
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null || sessionUser.getUserRole() != UserRole.AUDITOR) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return;
        }
    %>
    
    <main class="main-content">
        <div class="card">
            <div class="card-header">
                <h1 class="card-title">Auditor Dashboard</h1>
                <p class="card-subtitle">Compliance Monitoring & Audit Reports</p>
            </div>
            
            <div class="dashboard-grid">
                <div class="stat-card">
                    <div class="stat-number">23</div>
                    <div class="stat-label">Flagged Transactions</div>
                </div>
                <div class="stat-card">
                    <div class="stat-number">5</div>
                    <div class="stat-label">Compliance Issues</div>
                </div>
                <div class="stat-card">
                    <div class="stat-number">98.7%</div>
                    <div class="stat-label">Audit Score</div>
                </div>
            </div>
        </div>
        
        <div class="card">
            <div class="card-header">
                <h2 class="card-title">Audit Logs</h2>
                <p class="card-subtitle">System activity and compliance monitoring</p>
            </div>
            
            <div class="alert alert-error">
                <strong>Alert:</strong> 3 high-value transactions require immediate review
            </div>
            
            <div class="table-container">
                <table class="table">
                    <thead>
                        <tr>
                            <th>Timestamp</th>
                            <th>User</th>
                            <th>Action</th>
                            <th>Risk Level</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>2024-01-15 14:35:22</td>
                            <td>teller_001</td>
                            <td>Large Cash Withdrawal</td>
                            <td>High</td>
                            <td>Under Review</td>
                        </tr>
                        <tr>
                            <td>2024-01-15 14:30:15</td>
                            <td>manager_002</td>
                            <td>Account Override</td>
                            <td>Medium</td>
                            <td>Approved</td>
                        </tr>
                        <tr>
                            <td>2024-01-15 14:25:08</td>
                            <td>admin_001</td>
                            <td>User Permission Change</td>
                            <td>Medium</td>
                            <td>Logged</td>
                        </tr>
                        <tr>
                            <td>2024-01-15 14:20:33</td>
                            <td>teller_003</td>
                            <td>Multiple Failed Logins</td>
                            <td>High</td>
                            <td>Investigating</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        
        <div class="card">
            <div class="card-header">
                <h2 class="card-title">Compliance Reports</h2>
                <p class="card-subtitle">Available audit and compliance documents</p>
            </div>
            
            <div class="dashboard-grid">
                <button class="btn btn-primary">Generate Daily Report</button>
                <button class="btn btn-secondary">Transaction Analysis</button>
                <button class="btn btn-secondary">Risk Assessment</button>
                <button class="btn btn-secondary">Compliance Summary</button>
            </div>
        </div>
    </main>
    
    <jsp:include page="/includes/footer.jsp" />
</body>
</html>
