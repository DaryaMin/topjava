package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {
    protected abstract SK getSearchKey(Integer id);

    public void update(Meal meal) {
        SK searchKey = getSearchKeyIfExist(meal.getId());
        doUpdate(meal, searchKey);
        System.out.println("INFO: Meal with uuid = " + meal + " update successful");
    }

    protected abstract boolean isExist(SK searchKey);

    protected abstract void doUpdate(Meal meal, SK searchKey);

    public void save(Meal meal) {
        SK searchKey = getSearchKeyIfNotExist(meal.getId());
        doSave(meal, searchKey);
    }

    protected abstract void doSave(Meal meal, SK searchKey);

    public Meal get(Integer id) {
        SK searchKey = getSearchKeyIfExist(id);
        return doGet(searchKey);
    }

    protected abstract Meal doGet(SK searchKey);

    public void delete(Integer id) {
        SK searchKey = getSearchKeyIfExist(id);
        doDelete(searchKey);
        System.out.println("INFO: Meal with uuid = " + id + " delete successful");
    }

    public List<Meal> getAllSorted() {
        Comparator<Meal> mealComparator = Comparator.comparing(Meal::getDateTime).thenComparing(Meal::getId);
        List<Meal> list = doGetAll();
        list.sort(mealComparator);

        return list;
    }

    protected abstract List<Meal> doGetAll();

    protected abstract void doDelete(SK searchKey);

    private SK getSearchKeyIfExist(Integer id) {
        SK searchKey = getSearchKey(id);
        if (!isExist(searchKey)) {
            throw new RuntimeException("Записи " + id + " не найдена");
        }
        return searchKey;
    }

    private SK getSearchKeyIfNotExist(Integer id) {
        SK searchKey = getSearchKey(id);
        if (isExist(searchKey)) {
            throw new RuntimeException("Найдена запись " + id);
        }
        return searchKey;
    }

}

