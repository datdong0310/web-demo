<%-- 
    Document   : search_item
    Created on : Oct 18, 2025, 9:19:48 AM
    Author     : DONG DAT
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<body>
    <h2>Search Items</h2>

    <form action="${pageContext.request.contextPath}/item" method="get">
        <input type="hidden" name="action" value="search">
        <input type="text" name="keyword" value="${keyword}" placeholder="Enter item name">
        <button type="submit">Search</button>
    </form>

    <hr>

    <c:if test="${not empty itemList}">
        <table border="1">
            <tr><th>Name</th><th>Price</th><th>Action</th></tr>
            <c:forEach var="i" items="${itemList}">
                <tr>
                    <td>${i.name}</td>
                    <td>${i.sellingPrice}</td>
                    <td><a href="item?action=detail&id=${i.id}&keyword=${keyword}">View</a></td>
                </tr>
            </c:forEach>
        </table>
    </c:if>

    <c:if test="${empty itemList && not empty keyword}">
        <p>No items found for '<b>${keyword}</b>'.</p>
    </c:if>
</body>
</html>
