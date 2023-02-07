<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<html lang="ru">
<head>
    <title>Meals</title>
     <style>
       dt {
        float: left;
        width: 100px;
        text-align: right;
        padding-right: 5px;
        min-height: 1px;
       }
       dd {
        position: relative;
        top: -1px;
        margin-bottom: 10px;
       }
     </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meal</h2>
<section>
    <form method="post" action="meals" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="id" value="${meal.getId()}">
        <dt>DateTime:</dt>
        <dd><input type="datetime-local" name="dateTime" size=55 value="${meal.getDateTime()}" required></dd>
        <dt>Description</dt>
        <dd><input type="text" name="description" size=55 value="${meal.getDescription()}" required></dd>
        <dt>Calories</dt>
        <dd><input type="number" name="calories" size=55 value="${meal.getCalories()}" required></dd>

        <button type="submit">Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
</section>
</body>
</html>