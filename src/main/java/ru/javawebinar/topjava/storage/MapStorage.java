package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

public class MapStorage extends AbstractStorage<Integer> {
    private final Map<Integer, Meal> storage = new HashMap<>();

    public MapStorage() {
        storage.put(1, new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        storage.put(2, new Meal(2, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        storage.put(3, new Meal(3, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        storage.put(4, new Meal(4, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        storage.put(5, new Meal(5, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        storage.put(6, new Meal(6, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        storage.put(7, new Meal(7, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));

    }

    public Integer id = storage.keySet().stream().reduce(Integer::max).get();


    @Override
    protected Integer getSearchKey(Integer id) {
        return id;
    }

    protected Integer getId() {
        return id;
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return storage.containsKey(searchKey);
    }

    @Override
    protected void doUpdate(Meal meal, Integer searchKey) {
        storage.replace(searchKey, meal);
    }

    @Override
    protected void doSave(Meal m, Integer searchKey) {
        storage.put(searchKey, m);
    }

    @Override
    protected Meal doGet(Integer searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void doDelete(Integer searchKey) {
        storage.remove(searchKey);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Meal> doGetAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }
}
