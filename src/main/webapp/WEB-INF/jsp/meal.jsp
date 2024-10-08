<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meal</title>
</head>
<body>
<h3><a href="${pageContext.request.contextPath}/index.html">Home</a></h3>
<hr>
<h1>Edit meal</h1>
<form action="${pageContext.request.contextPath}/meals?id=${meal.id}" method="post">
    <label for="date"> DateTime:
        <input type="datetime-local" name="dateTime" id="date" value="${meal.dateTime}">
    </label><br>
    <label for="description"> Description:
        <input type="text" name="description" id="description" value="${meal.description}">
    </label><br>
    <label for="calories"> Calories:
        <input type="number" name="calories" id="calories" value="${meal.calories}">
    </label><br>

    <button type="submit">Save</button>
    <button onclick="window.history.back()" type="button">Cancel</button>
</form>

</body>
</html>
