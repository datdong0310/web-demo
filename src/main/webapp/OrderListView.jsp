<%-- 
    Document   : OrderListView
    Created on : Nov 2, 2025, 6:22:34?PM
    Author     : DONG DAT
--%>

<%@page import="com.mycompany.market.model.OnlineOrder"%>
<%@ page import="java.util.List" %>


<%
List<OnlineOrder> orders = (List<OnlineOrder>) request.getAttribute("orders");
%>

<h2>Pending Online Orders</h2>

<table border="1" width="90%">
    <tr>
        <th>ID</th>
        <th>Order Date</th>
        <th>Total Amount</th>
        <th>Customer</th>
        <th>Status</th>
        <th>Action</th>
    </tr>

    <% for (OnlineOrder o : orders) { %>
    <tr>
        <td><%= o.getId() %></td>
        <td><%= o.getOrderDate() %></td>
        <td><%= o.getTotalAmount() %></td>
        <td><%= o.getCustomer().getFullname() %></td>
        <td><%= o.getStatus() %></td>
        <td>
            <a href="OnlineOrderServlet?action=detail&id=<%=o.getId()%>">View Detail</a>
        </td>
    </tr>
    <% } %>
</table>

