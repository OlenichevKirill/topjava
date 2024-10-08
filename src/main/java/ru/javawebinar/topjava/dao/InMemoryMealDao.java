package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealDao implements MealDao {

    private final ConcurrentMap<Integer, Meal> mealConcurrentMap;
    private final AtomicInteger id = new AtomicInteger(1);

    public InMemoryMealDao() {
        mealConcurrentMap = new ConcurrentHashMap<>();
        List<Meal> mealsInitialisation = Arrays.asList(
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );
        mealsInitialisation.forEach(this::create);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealConcurrentMap.values());
    }

    @Override
    public Meal getById(int id) {
        return mealConcurrentMap.get(id);
    }

    @Override
    public Meal create(Meal meal) {
        Meal mealCreate = new Meal(getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories());
        mealConcurrentMap.put(mealCreate.getId(), mealCreate);
        return mealCreate;
    }

    @Override
    public Meal update(Meal meal) {
        return mealConcurrentMap.computeIfPresent(meal.getId(), (key, value) -> meal);
    }

    @Override
    public void delete(int id) {
        mealConcurrentMap.remove(id);
    }

    private int getId() {
        return id.getAndIncrement();
    }
}
