package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.RootController;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
public class JspMealController {
    private static final Logger log = LoggerFactory.getLogger(RootController.class);

    @Autowired
    private UserService service;

    @Autowired
    private MealService mealService;

    @GetMapping("/meals")
    public String getMeal(Model modelMeal, HttpServletRequest request) {
        log.info("meals");
        int userId = SecurityUtil.authUserId();
        String mealId = request.getParameter("id");
        if (mealId != null) {
            Meal meal = mealService.get(Integer.parseInt(mealId), userId);
            modelMeal.addAttribute("mealForm", meal);
            return "redirect: meals/create?id="+mealId;
        }
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        List<Meal> mealsDateFiltered = mealService.getBetweenInclusive(startDate, endDate, userId);
        List<MealTo> mealsTo = MealsUtil.getFilteredTos(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);
        modelMeal.addAttribute("meals", mealsTo);
        return "meals";
    }

    @PostMapping("/meals")
    public String createMeal(HttpServletRequest request) {
        log.info("createMealForm");
        String mealId = request.getParameter("id");
        int userId = SecurityUtil.authUserId();
        if (mealId != null  && !mealId.equals("") ) {
            mealService.delete(Integer.parseInt(mealId), userId);
            return "redirect:meals";
        }
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        mealService.create(meal, userId);
        return "redirect:mealForm";
    }

    @GetMapping("/meals/create")
    public String getMealForm(Model modelMeal, HttpServletRequest request) {
        log.info("createEmpty");
        String mealId = request.getParameter("id");
        int userId = SecurityUtil.authUserId();
        Meal meal;
        if (mealId != null  && !mealId.equals("") ) {
            meal = mealService.get(Integer.parseInt(mealId), userId);
        }  else {
            meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        }
        modelMeal.addAttribute("mealForm", meal);
        return "mealForm";
    }

    @PostMapping("/meals/create")
    public String setMeal(HttpServletRequest request) {
        int userId = SecurityUtil.authUserId();
        String mealId = request.getParameter("id");
        if (mealId != null && !mealId.equals("")) {
            log.info(mealId);
            Meal meal = new Meal(Integer.parseInt(mealId), LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories")));
            mealService.update(meal, userId);
            return "redirect:/meals";
        }
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        mealService.create(meal, userId);
        log.info("setMeal {}", meal);
        return "redirect:/meals";
    }
}
