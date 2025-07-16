<%@ page import="com.primebank.core.entity.User" %>
<%@ page import="com.primebank.core.entity.enums.UserRole" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create Customer - PrimeBank</title>
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
    <main class="custform-container">
        <div class="custform-card">
            <h1 class="custform-title">Create New Customer</h1>
            <div id="error-message" class="alert-error" style="margin-bottom: 10px; padding: 5px; display: none;"></div>

            <form id="customerForm" enctype="multipart/form-data" class="custform-form">

                <div class="custform-row">
                    <label for="fullName">Full Name</label>
                    <input type="text" name="fullName" id="fullName" required>
                </div>

                <div class="custform-row">
                    <label for="email">Email</label>
                    <input type="email" name="email" id="email" required>
                </div>

                <div class="custform-row">
                    <label for="address">Address</label>
                    <input type="text" name="address" id="address" required>
                </div>

                <div class="custform-row">
                    <label for="nic">NIC (For adults)</label>
                    <input type="text" name="nic" id="nic">
                </div>

                <div class="custform-row">
                    <label for="birthCertificateNo">Birth Certificate No (For children)</label>
                    <input type="text" name="birthCertificateNo" id="birthCertificateNo">
                </div>

                <div class="custform-row">
                    <label for="contactNo">Contact No</label>
                    <input type="text" name="contactNo" id="contactNo" required>
                </div>

                <div class="custform-row">
                    <label for="dateOfBirth">Date of Birth</label>
                    <input type="date" name="dateOfBirth" id="dateOfBirth" required>
                </div>

                <div class="custform-row">
                    <label for="gender">Gender</label>
                    <select name="gender" id="gender" required>
                        <option value="">Select gender</option>
                        <option value="MALE">Male</option>
                        <option value="FEMALE">Female</option>
                    </select>
                </div>

                <div class="custform-row">
                    <label for="photo">Upload Photo</label>
                    <input type="file" name="photo" id="photo" accept="image/*">
                </div>

                <div class="custform-actions">
                    <button type="submit" class="custform-btn-submit">Create Customer</button>
                    <a href="${pageContext.request.contextPath}/teller/dashboard.jsp" class="custform-btn-cancel">Cancel</a>
                </div>
            </form>
        </div>
    </main>

    <jsp:include page="/includes/footer.jsp" />
    <script>
        const contextPath = '${pageContext.request.contextPath}';
    </script>
    <script src="${pageContext.request.contextPath}/js/script.js"></script>

</body>
</html>
