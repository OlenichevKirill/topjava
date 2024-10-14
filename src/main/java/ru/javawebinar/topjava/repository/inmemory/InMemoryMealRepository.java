package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, 1));
        MealsUtil.mealsTwo.forEach(meal -> save(meal, 2));
    }

    @Override
    public Meal save(Meal meal, Integer userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }
        if (!meal.getUserId().equals(userId)) {
            return null;
        }
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, Integer userId) {
        Meal meal = repository.get(id);
        if (meal == null || !(meal.getUserId().equals(userId))) {
            return false;
        }
        return repository.remove(id, meal);
    }

    @Override
    public Meal get(int id, Integer userId) {
        Meal meal = repository.get(id);
        if (meal == null || !meal.getUserId().equals(userId)) {
            return null;
        }
        return meal;
    }

    @Override
    public List<Meal> getAll(Integer userId) {
        return getAllByPredicates(userId, meal -> true, meal -> true);
    }

    public List<Meal> getAll(Integer userId, LocalDateTime localDateTimeStart, LocalDateTime localDateTimeEnd) {
        return getAllByPredicates(userId, meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDateTime(), localDateTimeStart, localDateTimeEnd),
                meal -> DateTimeUtil.isBetweenHalfOpen(meal.getTime(), localDateTimeStart.toLocalTime(), localDateTimeEnd.toLocalTime()));
    }

    private List<Meal> getAllByPredicates(Integer userId, Predicate<Meal> filterByLocalDateTime, Predicate<Meal> filterByLocalTime) {
        return repository.values().stream()
                .filter(meal -> meal.getUserId().equals(userId))
                .filter(filterByLocalDateTime)
                .filter(filterByLocalTime)
                .sorted(Comparator.comparing(Meal::getDateTime).thenComparing(Meal::getTime).reversed())
                .collect(Collectors.toList());
    }
}

