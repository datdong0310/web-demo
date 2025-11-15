<%-- 
    Document   : item_detail
    Created on : Oct 18, 2025, 9:20:14 AM
    Author     : DONG DAT
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<body>
    <h2>Item Detail</h2>

    <c:if test="${not empty item}">
        <p><b>Name:</b> ${item.name}</p>
        <p><b>Price:</b> ${item.sellingPrice}</p>
        <p><b>Unit:</b> ${item.unit}</p>
        <p><b>Stock:</b> ${item.stockQuantity}</p>
        <p><b>Description:</b> ${item.description}</p>
        <img src="${item.image}" width="200">
    </c:if>

    <a href="item?action=search&keyword=${keyword}">Back to Search</a>

</body>
</html>
