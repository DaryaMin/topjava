<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meal</h2>
<section>
    <table border="1">
           <tr>
               <th>Дата/Время</th>
               <th>Описание</th>
               <th>Калории</th>
           </tr>
           <c:forEach items="${mealsTo}" var="mealTo">
               <jsp:useBean id="mealTo" type="ru.javawebinar.topjava.model.MealTo"/>
               <tr style="color: ${mealTo.getExcess() ? 'red' : 'green'}">
                   <td>${mealTo.getDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))}</td>
                   <td>${mealTo.getDescription()}</td>
                   <td>${mealTo.getCalories()}</td>
               </tr>
           </c:forEach>
    </table>
</section>
</body>
</html>