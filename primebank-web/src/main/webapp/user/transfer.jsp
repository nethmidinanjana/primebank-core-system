<%@ page import="com.primebank.core.entity.User" %>
<%@ page import="com.primebank.core.entity.enums.UserRole" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Fund Transfer - PrimeBank</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <%
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null || sessionUser.getUserRole() != UserRole.CUSTOMER) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return;
        }
    %>
    <jsp:include page="/includes/header.jsp"/>

    <div class="transferform-page-wrapper">
        <div class="transferform-container">
            <h2 class="transferform-title">Fund Transfer</h2>

            <div id="transfer-error" class="transferform-alert" style="display: none;"></div>

            <form id="transferForm" class="transferform-form">
                <div class="transferform-row">
                    <label for="fromAccount">Your Account Number</label>
                    <input type="text" id="fromAccount" name="fromAccount" required>
                </div>

                <div class="transferform-row">
                    <label for="toAccount">Recipient Account Number</label>
                    <input type="text" id="toAccount" name="toAccount" required>
                </div>

                <div class="transferform-row">
                    <label for="amount">Amount (Rs)</label>
                    <input type="number" id="amount" name="amount" required min="1" step="0.01">
                </div>

                <div class="transferform-row">
                    <label for="description">Description (optional)</label>
                    <textarea id="description" name="description" rows="3" placeholder="e.g., Utility payment, savings transfer..."></textarea>
                </div>

                <div class="transferform-actions">
                    <button type="button" class="transferform-btn-submit" onclick="submitTransfer()">Transfer</button>
                    <a href="${pageContext.request.contextPath}/user/dashboard.jsp" class="transferform-btn-cancel">Cancel</a>
                </div>
            </form>
        </div>
    </div>

    <jsp:include page="/includes/footer.jsp"/>
    <script>
        const contextPath = "${pageContext.request.contextPath}";
    </script>
    <script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>
