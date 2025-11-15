<%-- 
    Document   : Login
    Created on : Nov 2, 2025, 5:33:46 PM
    Author     : DONG DAT
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login Page</title>
    <style>
        body {
            font-family: Arial;
            background: #f3f3f3;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        form {
            background: white;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.2);
        }
        input[type=text], input[type=password] {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border-radius: 6px;
            border: 1px solid #ccc;
        }
        input[type=submit] {
            width: 100%;
            padding: 10px;
            background: #007bff;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
        }
        input[type=submit]:hover {
            background: #0056b3;
        }
        .error { color: red; }
    </style>
</head>
<body>
<form action="LoginServlet" method="post">
    <h2>Login</h2>
    <input type="text" name="username" placeholder="Username" required/>
    <input type="password" name="password" placeholder="Password" required/>
    <input type="submit" value="Login"/>
    <p class="error">${errorMessage}</p>
</form>
</body>
</html>