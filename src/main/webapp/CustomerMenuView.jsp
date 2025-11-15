<%-- 
    Document   : customer_menu
    Created on : Oct 18, 2025, 9:19:21 AM
    Author     : DONG DAT
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.mycompany.market.model.*" %>

<%
    Member user = (Member) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Customer Menu</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f4f6f9;
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 500px;
            background: #fff;
            margin: 60px auto;
            padding: 25px;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
            text-align: center;
        }

        h2 {
            margin-bottom: 10px;
            color: #333;
        }

        h3 {
            margin-top: 5px;
            color: #444;
        }

        p {
            margin: 5px 0;
            color: #555;
            font-size: 15px;
        }

        a.button {
            display: inline-block;
            margin-top: 20px;
            padding: 12px 22px;
            background: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 8px;
            font-size: 15px;
            transition: 0.3s;
        }

        a.button:hover {
            background: #0056b3;
        }

        hr {
            margin: 20px 0;
            border: none;
            border-top: 1px solid #ddd;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>Customer Menu</h2>

    <h3>Welcome, <%= user.getFullname() %>!</h3>
    <p>Email: <%= user.getEmail() %></p>
    <p>Phone: <%= user.getPhonenumber() %></p>

    <hr>

    <a href="search_item.jsp" class="button">Search Item</a>
</div>

</body>
</html>