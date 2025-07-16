<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.primebank.core.entity.User" %>
<%@ page import="com.primebank.core.entity.enums.UserRole" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create Account - PrimeBank</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <jsp:include page="/includes/header.jsp" />

    <%
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null || (
                sessionUser.getUserRole() != UserRole.ADMIN &&
                        sessionUser.getUserRole() != UserRole.MANAGER &&
                        sessionUser.getUserRole() != UserRole.TELLER
        )) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return;
        }
    %>

    <div class="accountform-container">
        <h2 class="accountform-title">Create New Account</h2>

        <div id="accountform-error-message" class="accountform-alert-box" style="display:none;"></div>

        <form id="accountForm" class="accountform-form">
            <div class="accountform-row">
                <label for="customerSearch">Customer NIC or Email</label>
                <input type="text" id="customerSearch" name="customerSearch" required>
            </div>

            <div class="accountform-row">
                <label for="accountType">Account Type</label>
                <select id="accountType" name="accountType" required>
                    <option value="">Select Type</option>
                    <option value="SAVINGS">Savings</option>
                    <option value="CURRENT">Current</option>
                    <option value="FIXED_DEPOSIT">Fixed Deposit</option>
                    <option value="CHILD">Child</option>
                </select>
            </div>

            <div class="accountform-row">
                <label for="initialDeposit">Initial Deposit</label>
                <input type="number" id="initialDeposit" name="initialDeposit" required min="1000" step="0.01">
            </div>

            <div class="accountform-row">
                <label for="guardianEmail">Guardian Email</label>
                <input type="email" id="guardianEmail" name="guardianEmail">
            </div>

            <div class="accountform-actions">
                <button type="button" class="accountform-btn-submit" onclick="createAccount()">Create Account</button>
                <a href="${pageContext.request.contextPath}/teller/dashboard.jsp" class="accountform-btn-cancel">Cancel</a>
            </div>
        </form>
    </div>

    <jsp:include page="/includes/footer.jsp" />
    <script>
        const contextPath = '${pageContext.request.contextPath}';
    </script>
    <script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
