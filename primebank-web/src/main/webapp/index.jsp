<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PrimeBank - Banking System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <jsp:include page="/includes/header.jsp" />
    
    <main class="main-content">
        <div class="card">
            <div class="card-header text-center">
                <h1 class="card-title">Welcome to PrimeBank</h1>
                <p class="card-subtitle">Professional Banking Management System</p>
            </div>
            
            <div class="text-center mb-3">
                <p>Select your role to access the appropriate dashboard:</p>
            </div>
            
            <div class="role-grid">
                <a href="${pageContext.request.contextPath}/admin/dashboard" class="role-card">
                    <div class="role-icon">👨‍💼</div>
                    <h3 class="role-title">Administrator</h3>
                    <p class="role-description">System administration and user management</p>
                </a>
                
                <a href="${pageContext.request.contextPath}/manager/dashboard.jsp" class="role-card">
                    <div class="role-icon">📊</div>
                    <h3 class="role-title">Manager</h3>
                    <p class="role-description">Branch management and oversight</p>
                </a>
                
                <a href="${pageContext.request.contextPath}/teller/dashboard.jsp" class="role-card">
                    <div class="role-icon">💰</div>
                    <h3 class="role-title">Teller</h3>
                    <p class="role-description">Customer transactions and services</p>
                </a>
                
                <a href="${pageContext.request.contextPath}/auditor/dashboard.jsp" class="role-card">
                    <div class="role-icon">🔍</div>
                    <h3 class="role-title">Auditor</h3>
                    <p class="role-description">Compliance and audit reports</p>
                </a>
                
                <a href="${pageContext.request.contextPath}/user/dashboard.jsp" class="role-card">
                    <div class="role-icon">👤</div>
                    <h3 class="role-title">Customer</h3>
                    <p class="role-description">Personal banking dashboard</p>
                </a>

            </div>
        </div>
    </main>
    
    <jsp:include page="/includes/footer.jsp" />
</body>
</html>
