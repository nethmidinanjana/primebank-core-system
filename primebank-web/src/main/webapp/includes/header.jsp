<%@ page import="com.primebank.core.entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<header class="header">
    <div class="header-content">
        <a href="${pageContext.request.contextPath}/" class="logo">
            PrimeBank
        </a>
        <div class="user-info">
            <%
                User sessionUser = (User) session.getAttribute("user");
                if (sessionUser != null) {
            %>
            <span class="welcome-message">
            Welcome, <%= sessionUser.getUsername() %>
        </span>
            <a href="<%= request.getContextPath() %>/logout.jsp" class="sign-out-btn">Sign Out</a>
            <%
            } else {
            %>
            <a href="<%= request.getContextPath() %>/auth/login.jsp" class="sign-in-btn">Log In</a>
            <%
                }
            %>
        </div>
    </div>
</header>
