package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealDao implements MealDao {

    private ConcurrentMap<Integer, Meal> mealConcurrentMap;
    private final AtomicInteger id = new AtomicInteger(1);


    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(getMealConcurrentMap().values());
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
        mealConcurrentMap.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public void delete(int id) {
        mealConcurrentMap.remove(id);
    }

    private ConcurrentMap<Integer, Meal> getMealConcurrentMap() {
        if (mealConcurrentMap == null) {
            List<Meal> meals = new ArrayList<>();
            meals.add(new Meal(getId(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
            meals.add(new Meal(getId(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
            meals.add(new Meal(getId(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
            meals.add(new Meal(getId(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
            meals.add(new Meal(getId(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
            meals.add(new Meal(getId(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
            meals.add(new Meal(getId(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));

            mealConcurrentMap = meals.stream().collect(Collectors.toConcurrentMap(Meal::getId, meal -> meal));
            return mealConcurrentMap;
        }
        return mealConcurrentMap;
    }

    private int getId() {
        return id.getAndIncrement();
    }
}
