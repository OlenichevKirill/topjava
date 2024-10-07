package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.dao.InMemoryMealDaoImpl;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MealInMemory;

import java.util.List;

public class InMemoryMealServiceImpl implements MealService {

    private static final InMemoryMealServiceImpl INSTANCE = new InMemoryMealServiceImpl();
    private final MealDao mealDao = InMemoryMealDaoImpl.getInstance();

    private InMemoryMealServiceImpl() {
    }

    @Override
    public List<Meal> getAll() {
        return mealDao.getAll();
    }

    @Override
    public Meal getById(int id) {
        return mealDao.getById(id);
    }

    @Override
    public void save(Meal meal) {
        Meal mealSave = new Meal(MealInMemory.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories());
        mealDao.save(mealSave);
    }

    @Override
    public void update(Meal meal) {
        mealDao.update(meal);
    }

    @Override
    public void delete(int id) {
        mealDao.delete(id);
    }

    public static InMemoryMealServiceImpl getInstance() {
        return INSTANCE;
    }
}
