<%-- 
    Document   : ProcessOrderView
    Created on : Nov 2, 2025, 6:37:32 PM
    Author     : DONG DAT
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.mycompany.market.model.*" %>
<%@ page import="java.util.*" %>

<%
    OnlineOrder order = (OnlineOrder) request.getAttribute("order");
    if (order == null) {
%>
    <h3>No order found!</h3>
<%
    return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Process Online Order</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 40px;
        }
        h2 {
            color: #444;
        }
        table {
            border-collapse: collapse;
            width: 90%;
            margin-bottom: 25px;
        }
        th, td {
            border: 1px solid #ccc;
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #eee;
        }
        .section {
            margin-bottom: 25px;
        }
        .btn {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 10px 15px;
            border-radius: 5px;
            text-decoration: none;
        }
        .btn:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>

<h2>Order Detail (Order ID: <%= order.getId() %>)</h2>

<div class="section">
    <h3>🧍 Customer Information</h3>
    <p><b>Name:</b> <%= order.getCustomer().getFullname() %></p>
    <p><b>Phone:</b> <%= order.getCustomer().getPhonenumber() %></p>
    <p><b>Address:</b> <%= order.getDeliveryAddress() %></p>
</div>

<div class="section">
    <h3>🛒 Order Items</h3>
    <table>
        <tr>
            <th>Item ID</th>
            <th>Item Name</th>
            <th>Quantity</th>
            <th>Price</th>
            <th>Subtotal</th>
        </tr>
        <%
            List<OrderDetail> details = order.getOrderDetail();
            boolean hasInsufficientStock = false; // Track if any item lacks stock

            for (OrderDetail d : details) {
                boolean insufficient = d.getQuantity() > d.getItem().getStockQuantity();
                if (insufficient) hasInsufficientStock = true;
        %>
        <tr>
            <td><%= d.getItem().getId() %></td>
            <td>
                <%= d.getItem().getName() %>
                <% if (insufficient) { %>
                    <span style="color: red; font-weight: bold;"> (Not enough stock!)</span>
                <% } %>
            </td>
            <td><%= d.getQuantity() %></td>
            <td><%= d.getPrice() %></td>
            <td><%= d.getSubtotal() %></td>
        </tr>
        <% } %>
    </table>
    <p><b>Total Amount:</b> <b><%= order.getTotalAmount() %></b></p>
</div>

<div class="section">
    <h3>🚚 Assign Delivery Staff</h3>

    <form action="DeliverStaffServlet" method="get">
        <input type="hidden" name="orderId" value="<%= order.getId() %>">
        <input type="hidden" name="customerName" value="<%= order.getCustomer().getFullname() %>">
        <input type="hidden" name="deliveryAddress" value="<%= order.getDeliveryAddress() %>">
        <input type="hidden" name="totalAmount" value="<%= order.getTotalAmount() %>">

        <button type="submit" class="btn"
            <%= hasInsufficientStock ? "disabled title='Cannot assign staff — not enough stock!'" : "" %>>
            Assign Delivery Staff →
        </button>
    </form>

    <% if (hasInsufficientStock) { %>
        <p style="color: red; font-weight: bold; margin-top: 10px;">
            ⚠ Cannot assign delivery staff. One or more items do not have enough stock!
        </p>
    <% } %>
</div>


<br>
<a href="OnlineOrderServlet?action=list" class="btn">⬅ Back to Order List</a>

</body>
</html>
