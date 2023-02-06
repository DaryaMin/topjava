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
    <button><a href="meals?action=add">
            <img src="img/add.png" alt="" style="vertical-align:middle">
            Добавить Запись</a>
    </button>
    <table border="1">
           <tr>
               <th>Дата/Время</th>
               <th>Описание</th>
               <th>Калории</th>
               <th>Удалить</th>
               <th>Редактировать</th>
           </tr>
           <c:forEach items="${mealsTo}" var="mealTo">
               <jsp:useBean id="mealTo" type="ru.javawebinar.topjava.model.MealTo"/>
               <tr style="color: ${mealTo.getExcess() ? 'red' : 'green'}">
                   <td>${mealTo.getDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))}</td>
                   <td>${mealTo.getDescription()}</td>
                   <td>${mealTo.getCalories()}</td>
                   <td><a href="meals?id=${mealTo.getId()}&action=delete"><img src="img/delete.png"></a></td>
                   <td><a href="meals?id=${mealTo.getId()}&action=edit"><img src="img/pencil.png"></a></td>
               </tr>
           </c:forEach>
    </table>
</section>
</body>
</html>