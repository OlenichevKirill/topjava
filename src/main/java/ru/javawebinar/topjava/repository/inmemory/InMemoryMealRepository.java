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
    public Meal save(Meal meal, int userId) {
        meal.setUserId(userId);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        Meal mealGet = repository.get(meal.getId());
        return !mealGet.getUserId().equals(userId) ? null : repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        Meal meal = repository.get(id);
        return meal != null && meal.getUserId().equals(userId) && repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = repository.get(id);
        return (meal == null || !meal.getUserId().equals(userId)) ? null : meal;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getAllByPredicates(userId, meal -> true);
    }

    public List<Meal> getAllByFilter(int userId, LocalDateTime localDateTimeStart, LocalDateTime localDateTimeEnd) {
        return getAllByPredicates(userId, meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDateTime(), localDateTimeStart, localDateTimeEnd));
    }

    private List<Meal> getAllByPredicates(Integer userId, Predicate<Meal> filterByLocalDateTime) {
        return repository.values().stream()
                .filter(meal -> meal.getUserId().equals(userId))
                .filter(filterByLocalDateTime)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

