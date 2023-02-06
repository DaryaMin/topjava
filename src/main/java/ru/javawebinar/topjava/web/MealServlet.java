package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MapStorage;
import ru.javawebinar.topjava.storage.Storage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private final Storage storage = new MapStorage();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to users");

        String id = request.getParameter("id");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("mealsTo", MealsUtil.filteredByStreams(storage.getAllSorted()));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
            return;
        }
        Meal m;
        switch (action) {
            case "delete":
                System.out.println(id);
                storage.delete(Integer.parseInt(id));
                response.sendRedirect("meals");
                return;
            case "view":
                m = storage.get(Integer.parseInt(id));
                break;
            case "add":
                m = Meal.EMPTY;
                break;
            case "edit":
                m = storage.get(Integer.parseInt(id));
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("meal", m);
        request.getRequestDispatcher(("view".equals(action) ? "/mealsView.jsp" : "/mealsEdit.jsp")).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        String description = request.getParameter("description");
        String calories = request.getParameter("calories");
        String dateTime = request.getParameter("dateTime");

        Meal m;

        if (id == null || id.length() == 0) {
            m = new Meal(LocalDateTime.parse(dateTime), description, Integer.parseInt(calories));
        } else {
            m = new Meal(Integer.parseInt(id), LocalDateTime.parse(dateTime), description, Integer.parseInt(calories));
        }

        if (id == null || id.length() == 0) {
            storage.save(m);
        } else {
            storage.update(m);
        }
        response.sendRedirect("meals");
    }

}
