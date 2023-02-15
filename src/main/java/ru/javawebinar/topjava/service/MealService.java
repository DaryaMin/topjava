package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal) {
        return repository.save(meal, meal.getUserId());
    }

    public void delete(int id, int userId) {
        checkNotFound(repository.delete(id, userId), "Не найдена запись");
    }

    public Meal get(int id, int  userId) {
        return checkNotFound(repository.get(id, userId), "Не найдена запись");
    }

    public List<Meal> getAll(int userId) {
        return (List<Meal>) repository.getAll(userId);
    }

    public void update(Meal meal, int userId) {
        checkNotFound(repository.save(meal, userId), "Не найдена запись");
    }

}