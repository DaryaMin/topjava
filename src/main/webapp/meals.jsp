<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meal</h2>
<table>
    <caption>Список всех резюме</caption>
           <tr>
               <th>dateTime</th>
               <th>description</th>
               <th>calories</th>
               <th>excess</th>
           </tr>
           <c:forEach items="${mealsTo}" var="mealTo">
               <jsp:useBean id="mealTo" type="ru.javawebinar.topjava.model.MealTo.java"/>
               <tr
                   <td><${mealTo.getDateTime()}</a></td>
                   <td><${mealTo.getDescription()}></td>
                   <td><${mealTo.getCalories()}></td>
                   <td><${mealTo.getExcess()}></td>
               </tr>
           </c:forEach>
</table>
</body>
</html>