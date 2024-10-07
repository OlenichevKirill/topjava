package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MealInMemory {

    private static ConcurrentMap<Integer, Meal> mealConcurrentMap;
    private static final AtomicInteger id = new AtomicInteger(1);

    private MealInMemory() {
    }

    public static ConcurrentMap<Integer, Meal> getMealConcurrentMap() {
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

    public static int getId() {
        return id.getAndIncrement();
    }
}
