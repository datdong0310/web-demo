<%-- 
    Document   : AssignDeliveryStaff
    Created on : Nov 2, 2025, 8:46:21 PM
    Author     : DONG DAT
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.mycompany.market.model.*" %>
<%@ page import="java.util.*" %>

<%
    OnlineOrder order = (OnlineOrder) request.getAttribute("order");
    List<DeliveryStaff> staffList = (List<DeliveryStaff>) request.getAttribute("staffList");

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
    <title>Assign Delivery Staff</title>
    <style>
        body { font-family: Arial; margin: 40px; }
        h2 { color: #444; }
        table { border-collapse: collapse; width: 80%; margin-top: 20px; }
        th, td { border: 1px solid #ccc; padding: 10px; }
        th { background-color: #eee; }
        .btn { background-color: #007bff; color: white; padding: 10px 15px; border: none; border-radius: 5px; text-decoration: none; }
        .btn:hover { background-color: #0056b3; }
        tr:hover { background-color: #f1f1f1; }
    </style>
</head>
<body>

<h2>Assign Delivery Staff for Order #<%= order.getId() %></h2>

<p><b>Customer:</b> <%= order.getCustomer().getFullname() %></p>
<p><b>Total Amount:</b> $<%= order.getTotalAmount() %></p>

<!-- Remove the outer form -->
<table>
<tr>
    <th>ID</th>
    <th>Name</th>
    <th>Phone</th>
    <th>Current Assign</th>
</tr>
<% 
    if (staffList != null && !staffList.isEmpty()) {
        for (DeliveryStaff s : staffList) {
%>
<tr onclick="assignStaff(<%= s.getId() %>, '<%= s.getFullname() %>')" style="cursor:pointer;">
    <td><%= s.getId() %></td>
    <td><%= s.getFullname() %></td>
    <td><%= s.getPhonenumber() %></td>
    <td><%= s.getCurrentAssign() %></td>
</tr>
<% 
        }
    } else { 
%>
<tr><td colspan="4">No delivery staff available.</td></tr>
<% } %>
</table>

<!-- ✅ Hidden form (now outside) -->
<form id="assignForm" action="DeliverStaffServlet" method="post" style="display:none;">
    <input type="hidden" name="orderId" value="<%= order.getId() %>">
    <input type="hidden" name="staffId" id="staffId">
    <input type="hidden" name="staffName" id="staffName">
</form>

<script>
function assignStaff(id, name) {
    document.getElementById('staffId').value = id;
    document.getElementById('staffName').value = name;
    document.getElementById('assignForm').submit();
}
</script>

<br>
<a href="OnlineOrderServlet?action=detail&id=<%= order.getId() %>" class="btn">⬅ Back</a>

</body>
</html>