package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MealInMemory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

public class InMemoryMealDaoImpl implements MealDao {

    private static final InMemoryMealDaoImpl INSTANCE = new InMemoryMealDaoImpl();

    ConcurrentMap<Integer, Meal> mealConcurrentMap = MealInMemory.getMealConcurrentMap();

    private InMemoryMealDaoImpl() {
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(MealInMemory.getMealConcurrentMap().values());
    }

    @Override
    public Meal getById(int id) {
        return mealConcurrentMap.get(id);
    }

    @Override
    public void save(Meal meal) {
        mealConcurrentMap.put(meal.getId(), meal);
    }

    @Override
    public void update(Meal meal) {
        mealConcurrentMap.put(meal.getId(), meal);
    }

    @Override
    public void delete(int id) {
        mealConcurrentMap.remove(id);
    }

    public static InMemoryMealDaoImpl getInstance() {
        return INSTANCE;
    }
}
