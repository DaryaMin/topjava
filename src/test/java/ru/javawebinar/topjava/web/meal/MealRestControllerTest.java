package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjava.MatcherFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;
import static ru.javawebinar.topjava.util.MealsUtil.getTos;
import static ru.javawebinar.topjava.web.meal.MealRestController.REST_URL;

public class MealRestControllerTest extends AbstractControllerTest {
    @Autowired
    private MealService mealService;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + meal1.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MatcherFactory.usingIgnoringFieldsComparator(Meal.class, "user").contentJson(meal1));
    }

    @Test
    void getAll() throws Exception {
        List<MealTo> mealsTo = getTos(meals, DEFAULT_CALORIES_PER_DAY);
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpectAll(MatcherFactory.usingIgnoringFieldsComparator(List.class, "user").contentJson(mealsTo));
    }

    @Test
    void delete() throws Exception {
        String id = String.valueOf(meal1.id());
        perform(MockMvcRequestBuilders.delete(REST_URL).param("id", id))
                .andDo(print())
                .andExpect(status().isNoContent());
        List<Meal> mealExpected = Stream.of(meal2, meal3,meal4,meal5,meal6,meal7)
                .sorted(Comparator.comparingInt(Meal::getId)
                .reversed())
                .collect(Collectors.toList());
        MEAL_MATCHER.assertMatch(mealService.getAll(SecurityUtil.authUserId()), mealExpected);
    }

    @Test
    void update() throws Exception {
        String id = String.valueOf(meal1.id());
        int userId = SecurityUtil.authUserId();
        Meal meal = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + "/" + id).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(meal)))
                .andDo(print());
        MEAL_MATCHER.assertMatch(mealService.get(Integer.parseInt(id), userId), meal);
    }

    @Test
    void create() throws Exception {
        Meal meal = getNew();
        MockHttpServletResponse response = perform(MockMvcRequestBuilders.post(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(meal)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse();
        Meal createdMeal = JsonUtil.readValue(response.getContentAsString(), Meal.class);
        MatcherFactory.usingIgnoringFieldsComparator(Meal.class, "id", "user").assertMatch(mealService.get(createdMeal.getId(), SecurityUtil.authUserId()), meal);
    }

}
