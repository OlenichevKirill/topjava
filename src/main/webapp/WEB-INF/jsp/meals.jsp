<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h1>Meals</h1><br>
<br>

<table border="1">
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    <c:forEach var="meal" items="${requestScope.mealTo}">
        <c:choose>
            <c:when test="${meal.excess}">
                <tr style="color: red">
                    <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime"
                                   type="both"/>
                    <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${parsedDateTime}"/></td>
                    <td>${meal.description}</td>
                    <td>${meal.calories}</td>
                </tr>
            </c:when>
            <c:otherwise>
                <tr style="color: green">
                    <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime"
                                   type="both"/>
                    <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${parsedDateTime}"/></td>
                    <td>${meal.description}</td>
                    <td>${meal.calories}</td>
                </tr>
            </c:otherwise>
        </c:choose>

    </c:forEach>
</table>
</body>
</html>
