<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - PrimeBank</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <div class="login-container">
        <div class="login-card">
            <div class="login-header">
                <h1 class="login-title">PrimeBank</h1>
                <p class="login-subtitle">Secure Banking Login</p>
            </div>
            
            <% if (request.getParameter("error") != null) { %>
                <div class="alert alert-error">
                    Invalid username or password. Please try again.
                </div>
            <% } %>
            
            <form action="${pageContext.request.contextPath}/auth/login" method="post">
                <div class="form-group">
                    <label for="username" class="form-label">Username</label>
                    <input 
                        type="text" 
                        id="username"
                        name="username"
                        class="form-input" 
                        required 
                        autocomplete="username"
                        placeholder="Enter your username"
                    >
                </div>
                
                <div class="form-group">
                    <label for="password" class="form-label">Password</label>
                    <input 
                        type="password" 
                        id="password"
                        name="password"
                        class="form-input" 
                        required 
                        autocomplete="current-password"
                        placeholder="Enter your password"
                    >
                </div>
                
                <button type="submit" class="btn btn-primary">
                    Sign In Securely
                </button>
            </form>
            
            <div class="text-center mt-3">
                <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">
                    Back to Home
                </a>
            </div>
        </div>
    </div>
</body>
</html>
