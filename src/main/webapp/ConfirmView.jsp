<%-- 
    Document   : ConfirmView
    Created on : Nov 9, 2025, 8:18:55 PM
    Author     : DONG DAT
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.mycompany.market.model.*" %>
<%@ page import="java.util.*" %>

<%
    OnlineOrder order = (OnlineOrder) request.getAttribute("order");
    String errorMessage = (String) request.getAttribute("errorMessage");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Confirm Order</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        h2, h3 { text-align: center; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #444; padding: 8px; text-align: left; }
        th { background-color: #f0f0f0; }
        hr { margin: 30px 0; }
        .summary { margin-top: 20px; text-align: right; }
        .no-print { margin-top: 30px; text-align: center; }
        .signature-section { margin-top: 60px; display: flex; justify-content: space-between; }
        .signature-box { width: 45%; text-align: center; }
        .signature-line { margin-top: 80px; border-top: 1px solid #000; width: 80%; margin-left: auto; margin-right: auto; }
        @media print { .no-print { display: none; } body { margin: 20px; } }
        button { padding: 8px 18px; font-size: 15px; margin: 6px; cursor: pointer; }
        .save-print { background-color: #4CAF50; color: white; border: none; }
        .cancel { background-color: #f44336; color: white; border: none; }
        .error-box { color: red; border:1px solid red; padding:10px; margin:15px 0; }
    </style>
    <script>
        function saveAndPrint() {
            const form = document.getElementById("saveForm");

            // Log in browser console (optional)
            console.log("Submitting orderId=" + form.orderId.value + ", staffId=" + form.staffId.value);

            // Submit normally to servlet
            form.submit();

            // Print after short delay to allow servlet to process
            setTimeout(() => {
                window.print();
            }, 500);
        }

        function cancelAction() {
            window.history.back();
        }
    </script>
</head>
<body>

<% if (errorMessage != null) { %>
<div class="error-box">
    <strong>Error:</strong> <%= errorMessage %>
</div>
<% } %>

<% if (order == null) { %>
<h3>No order found!</h3>
<% } else { %>

<h2>Order Confirmation</h2>
<p><strong>Order ID:</strong> <%= order.getId() %></p>
<p><strong>Customer:</strong> <%= order.getCustomer().getFullname() %></p>
<p><strong>Delivery Address:</strong> <%= order.getDeliveryAddress() %></p>
<p><strong>Delivery Staff:</strong> <%= order.getDeliverStaff() != null ? order.getDeliverStaff().getFullname() : "N/A" %></p>
<p><strong>Warehouse Staff:</strong> <%= order.getWareHouseStaff() != null ? order.getWareHouseStaff().getFullname() : "N/A" %></p>
<p><strong>Status:</strong> <%= order.getStatus() %></p>
<p><strong>Process Date:</strong> <%= order.getProcessDate() != null ? order.getProcessDate() : "N/A" %></p>

<hr/>

<h3>Order Details</h3>
<table>
    <tr>
        <th>Item</th>
        <th>Quantity</th>
        <th>Price</th>
        <th>Sale</th>
        <th>Subtotal</th>
    </tr>
    <%
        List<OrderDetail> details = order.getOrderDetail();
        if (details != null) {
            for (OrderDetail detail : details) {
                Item item = detail.getItem();
    %>
    <tr>
        <td><%= item != null ? item.getName() : "N/A" %></td>
        <td><%= detail.getQuantity() %></td>
        <td><%= detail.getPrice() %></td>
        <td><%= detail.getSale() %></td>
        <td><%= detail.getSubtotal() %></td>
    </tr>
    <%      }
        }
    %>
</table>

<div class="summary">
    <p><strong>Total Amount:</strong> $<%= order.getTotalAmount() %></p>
</div>

<div class="signature-section">
    <div class="signature-box">
        <p><strong>Customer Signature</strong></p>
        <div class="signature-line"></div>
        <p>(<%= order.getCustomer().getFullname() %>)</p>
    </div>
    <div class="signature-box">
        <p><strong>Company Representative</strong></p>
        <div class="signature-line"></div>
        <p>(<%= order.getDeliverStaff() != null ? order.getDeliverStaff().getFullname() : "N/A" %>)</p>
    </div>
</div>

<div class="no-print">
    <form id="saveForm" method="post" action="<%= request.getContextPath() %>/OnlineOrderServlet">
        <input type="hidden" name="action" value="save">
        <input type="hidden" name="orderId" value="<%= order.getId() %>">
        <input type="hidden" name="staffId" value="<%= order.getDeliverStaff() != null ? order.getDeliverStaff().getId() : "" %>">

        <button type="button" class="save-print" onclick="saveAndPrint()">💾 Save & Print</button>
        <button type="button" class="cancel" onclick="cancelAction()">❌ Cancel</button>
    </form>
</div>

<% } %>
</body>
</html>